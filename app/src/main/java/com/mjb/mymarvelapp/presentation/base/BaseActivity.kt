package com.mjb.mymarvelapp.presentation.base

//import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.mjb.mymarvelapp.core.AndroidApplication
//import com.mjb.mymarvelapp.core.di.component.ViewComponent
//import com.mjb.mymarvelapp.core.di.module.ViewModule

abstract class BaseActivity : AppCompatActivity()

/*abstract class BaseActivity : AppCompatActivity() {

    abstract fun initializeInjector(viewComponent: ViewComponent)

    private val activityComponent by lazy {
        (application as AndroidApplication)
            .appComponent
            .viewComponent(ViewModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector(activityComponent)
    }
}*/
