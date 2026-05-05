package com.oemam.footballapp.core.di

import androidx.room.Room
import com.oemam.footballapp.core.data.api.FootballApi
import com.oemam.footballapp.core.data.local.AppDatabase
import com.oemam.footballapp.core.data.repository.TeamRepositoryImpl
import com.oemam.footballapp.core.domain.repository.TeamRepository
import com.oemam.footballapp.core.domain.usecase.GetFavoriteTeamsUseCase
import com.oemam.footballapp.core.domain.usecase.GetTeamsUseCase
import com.oemam.footballapp.core.domain.usecase.ToggleFavoriteUseCase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val cf = CertificateFactory.getInstance("X.509")
        val certInputStream = androidContext().resources.openRawResource(com.oemam.footballapp.core.R.raw.thesportsdb)
        val certificate = certInputStream.use { cf.generateCertificate(it) }

        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("thesportsdb", certificate)
        }

        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }

        val trustManagers = tmf.trustManagers
        val x509TrustManager = trustManagers.filterIsInstance<X509TrustManager>().firstOrNull()
            ?: throw IllegalStateException("No X509TrustManager found")

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, arrayOf(x509TrustManager), null)
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(FootballApi::class.java) }
}

val databaseModule = module {
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("footballapp".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "football_db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
    single { get<AppDatabase>().teamDao() }
}

val repositoryModule = module {
    single<TeamRepository> { TeamRepositoryImpl(get(), get()) }
}

val useCaseModule = module {
    factory { GetTeamsUseCase(get()) }
    factory { GetFavoriteTeamsUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
}
