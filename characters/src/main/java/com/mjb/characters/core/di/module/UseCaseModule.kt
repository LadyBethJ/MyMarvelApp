package com.mjb.characters.core.di.module

import com.mjb.characters.domain.repository.CharactersRepository
import com.mjb.characters.domain.usecase.GetCharactersListUseCase
import com.mjb.characters.domain.usecase.GetCharacterDetailUseCase
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
