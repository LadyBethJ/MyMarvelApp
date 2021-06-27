package com.mjb.characters.core.di.repository

import com.mjb.characters.data.service.CharactersService
import org.koin.dsl.module

val characterServiceModule = module {
    factory { CharactersService(get()) }
}