package com.mjb.mymarvelapp.infrastructure

import android.app.Application
import com.mjb.mymarvelapp.infrastructure.di.component.ApplicationComponent
import com.mjb.mymarvelapp.infrastructure.di.component.DaggerApplicationComponent
import com.mjb.mymarvelapp.infrastructure.di.module.ApplicationModule

class AndroidApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}
