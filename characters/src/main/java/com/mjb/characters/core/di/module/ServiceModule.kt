package com.mjb.characters.core.di.module

import com.mjb.characters.data.service.CharactersApi
import com.mjb.characters.data.service.CharactersService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ServiceModule {

    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersApi = CharactersService(retrofit)

}