package com.mjb.mymarvelapp.core.di.module

import android.app.Activity
import com.mjb.mymarvelapp.core.di.scope.ViewScope
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