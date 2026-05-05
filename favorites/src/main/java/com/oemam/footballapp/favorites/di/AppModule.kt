package com.oemam.footballapp.favorites.di

import com.oemam.footballapp.favorites.presentation.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}