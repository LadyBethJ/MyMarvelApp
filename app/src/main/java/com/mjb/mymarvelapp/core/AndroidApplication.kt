package com.mjb.mymarvelapp.core

import android.app.Application
import com.mjb.characters.core.di.module.characterRepositoryModule
import com.mjb.characters.core.di.module.characterServiceModule
import com.mjb.characters.core.di.module.characterUseCasesModule
import com.mjb.characters.core.di.module.charactersApiModule
import com.mjb.core.di.module.networkModule
import com.mjb.mymarvelapp.core.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(
                getModules()
            )
        }
    }

    private fun getModules(): List<Module> {
        val appModules = mutableListOf(viewModelModule)
        val charactersModules = mutableListOf(
            characterUseCasesModule,
            characterRepositoryModule,
            characterServiceModule,
            charactersApiModule
        )

        val coreModules = mutableListOf(networkModule)

        val modules = mutableListOf<Module>()
        modules.addAll(appModules)
        modules.addAll(charactersModules)
        modules.addAll(coreModules)

        return modules
    }
}
