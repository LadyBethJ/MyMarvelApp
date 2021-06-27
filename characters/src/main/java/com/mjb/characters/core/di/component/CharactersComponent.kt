package com.mjb.characters.core.di.component

import com.mjb.characters.core.di.module.DataModule
import com.mjb.characters.core.di.module.ServiceModule
import com.mjb.characters.core.di.module.UseCaseModule
import com.mjb.core.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        UseCaseModule::class,
        ServiceModule::class
    ]
)
interface CharactersComponent {
}