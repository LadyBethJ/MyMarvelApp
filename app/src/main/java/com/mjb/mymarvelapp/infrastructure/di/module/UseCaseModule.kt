package com.mjb.mymarvelapp.infrastructure.di.module

import com.mjb.mymarvelapp.data.repository.CharactersRepository
import com.mjb.mymarvelapp.domain.usecase.GetCharactersListUseCase
import com.mjb.mymarvelapp.domain.usecase.GetCharacterDetailUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetCharactersListUseCase(charactersRepository: CharactersRepository): GetCharactersListUseCase =
        GetCharactersListUseCase(charactersRepository)

    @Provides
    fun provideGetCharacterDetailUseCase(charactersRepository: CharactersRepository): GetCharacterDetailUseCase =
        GetCharacterDetailUseCase(charactersRepository)

}
