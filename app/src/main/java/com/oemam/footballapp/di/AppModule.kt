package com.oemam.footballapp.di

import com.oemam.footballapp.presentation.viewmodel.DetailViewModel
import com.oemam.footballapp.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}