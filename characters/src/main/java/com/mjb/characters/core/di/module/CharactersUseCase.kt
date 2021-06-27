package com.mjb.characters.core.di.module

import com.mjb.characters.domain.usecase.GetCharacterDetailUseCase
import com.mjb.characters.domain.usecase.GetCharactersListUseCase
import org.koin.dsl.module

val characterUseCasesModule = module {

    factory { GetCharactersListUseCase(get()) }

    factory { GetCharacterDetailUseCase(get()) }
}