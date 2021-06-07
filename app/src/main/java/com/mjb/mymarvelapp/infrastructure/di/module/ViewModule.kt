package com.mjb.mymarvelapp.infrastructure.di.module

import android.app.Activity
import com.mjb.mymarvelapp.infrastructure.di.scope.ViewScope
import com.mjb.mymarvelapp.presentation.base.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ViewModule(private val activity: BaseActivity) {

    @Provides
    @ViewScope
    internal fun activity(): Activity {
        return activity
    }
}
