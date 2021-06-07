package com.mjb.mymarvelapp.infrastructure.di.component

import com.mjb.mymarvelapp.infrastructure.AndroidApplication
import com.mjb.mymarvelapp.infrastructure.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, NetworkModule::class, DataModule::class, UseCaseModule::class]
)
interface ApplicationComponent {

    fun inject(application: AndroidApplication)

    fun viewComponent(viewModule: ViewModule): ViewComponent
}
