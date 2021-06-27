package com.mjb.mymarvelapp.core

import android.app.Application
import com.mjb.characters.core.di.component.CharactersComponent
import com.mjb.characters.core.di.provider.CharactersComponentProvider
import com.mjb.core.di.module.NetworkModule
import com.mjb.mymarvelapp.core.di.component.ApplicationComponent
import com.mjb.mymarvelapp.core.di.component.DaggerApplicationComponent
import com.mjb.mymarvelapp.core.di.module.ApplicationModule

class AndroidApplication : Application(),
    CharactersComponentProvider
{

    private val networkModule: NetworkModule by lazy {
        // TODO ver cómo quitarle el application o el by lazy
        NetworkModule(this)
    }

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(networkModule)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun getCharactersComponent(): CharactersComponent {
        return DaggerCharactersComponent.builder()
            .networkModule(networkModule)
            .build()
    }
}
