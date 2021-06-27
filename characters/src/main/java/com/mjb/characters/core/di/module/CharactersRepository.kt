package com.mjb.characters.core.di.module

import com.mjb.characters.data.repository.CharactersRepositoryImpl
import com.mjb.characters.domain.repository.CharactersRepository
import org.koin.dsl.module

val characterRepositoryModule = module {
    factory<CharactersRepository> { CharactersRepositoryImpl(get(), get()) }
}