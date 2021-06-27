package com.mjb.mymarvelapp.core.di.module

import com.mjb.mymarvelapp.presentation.charactersList.viewmodel.CharactersListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CharactersListViewModel(get()) }
}