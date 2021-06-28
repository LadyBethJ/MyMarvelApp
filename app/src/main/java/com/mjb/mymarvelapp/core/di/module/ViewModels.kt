package com.mjb.mymarvelapp.core.di.module

import com.mjb.mymarvelapp.presentation.characterDetail.viewmodel.CharacterDetailViewModel
import com.mjb.mymarvelapp.presentation.charactersList.viewmodel.CharactersListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { CharactersListViewModel(get()) }

    viewModel { CharacterDetailViewModel(get()) }
}