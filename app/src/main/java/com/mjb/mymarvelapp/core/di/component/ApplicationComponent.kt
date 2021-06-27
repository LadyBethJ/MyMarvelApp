package com.mjb.mymarvelapp.core.di.component

import com.mjb.characters.core.di.module.UseCaseModule
import com.mjb.mymarvelapp.core.AndroidApplication
import com.mjb.mymarvelapp.core.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(application: AndroidApplication)

    fun viewComponent(viewModule: ViewModule): ViewComponent
}
