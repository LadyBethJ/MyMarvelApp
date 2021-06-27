package com.mjb.characters.core.di.module

import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.domain.repository.CharactersRepository
import com.mjb.characters.data.service.CharactersApi
import com.mjb.core.network.NetworkHandler
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCharactersRepository(
        characterApi: CharactersApi,
        networkHandler: NetworkHandler
    ): CharactersRepository = CharactersRepositoryImpl(characterApi, networkHandler)

}
