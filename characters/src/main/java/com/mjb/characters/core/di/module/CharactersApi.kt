package com.mjb.characters.core.di.module

import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.data.service.CharactersApi
import com.mjb.characters.data.service.CharactersService
import com.mjb.characters.domain.repository.CharactersRepository
import org.koin.dsl.module

val charactersApiModule = module {
    factory<CharactersApi> { CharactersService(get()) }
}