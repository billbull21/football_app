# Add project specific ProGuard rules here.

# ============================================================
# LeakCanary — heap analysis library (debug only)
# R8 strips its ContentProvider auto-init when minification is on
# ============================================================
-keep class leakcanary.** { *; }
-keep class shark.** { *; }
-keep class com.squareup.leakcanary.** { *; }
-dontwarn leakcanary.**
-dontwarn shark.**

# ============================================================
# Kotlin stdlib — keep intact for dynamic feature split APKs
# R8 merging these causes IllegalAccessError in split_*.apk
# ============================================================
-keep class kotlin.** { *; }
-keep class kotlin.jvm.** { *; }
-keep class kotlin.jvm.internal.** { *; }
-dontwarn kotlin.**

# ============================================================
# Koin dependency injection
# ============================================================
-keep class org.koin.** { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }
-keepnames class * extends androidx.lifecycle.ViewModel

# ============================================================
# App classes — keep all to avoid Koin reflection failures
# ============================================================
-keep class com.oemam.footballapp.** { *; }

# ============================================================
# Retrofit + Gson
# ============================================================
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Keep data classes used as Gson models (TeamDto, TeamResponse, etc.)
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ============================================================
# Room database
# ============================================================
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-dontwarn androidx.room.**

# ============================================================
# SQLCipher
# ============================================================
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.** { *; }
-dontwarn net.sqlcipher.**

# ============================================================
# Kotlin Coroutines
# ============================================================
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory { *; }
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler { *; }
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# ============================================================
# Keep line numbers for readable crash reports
# ============================================================
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
