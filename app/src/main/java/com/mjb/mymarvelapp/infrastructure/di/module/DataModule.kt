package com.mjb.mymarvelapp.infrastructure.di.module

import com.mjb.mymarvelapp.data.datasource.api.service.CharactersApi
import com.mjb.mymarvelapp.data.repository.*
import com.mjb.mymarvelapp.infrastructure.network.NetworkHandler
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
