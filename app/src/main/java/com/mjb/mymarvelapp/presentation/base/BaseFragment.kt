package com.mjb.mymarvelapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mjb.mymarvelapp.infrastructure.AndroidApplication
import com.mjb.mymarvelapp.infrastructure.di.component.ViewComponent
import com.mjb.mymarvelapp.infrastructure.di.module.ViewModule
import com.mjb.mymarvelapp.presentation.MainActivity

abstract class BaseFragment : Fragment() {

    protected abstract fun initializeInjector(viewComponent: ViewComponent)

    private val fragmentComponent by lazy {
        (activity?.application as AndroidApplication)
            .appComponent
            .viewComponent(ViewModule(activity as BaseActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector(fragmentComponent)
    }

    protected fun showSpinner(show: Boolean?) {
        when (show) {
            true -> showProgress()
            false -> hideProgress()
        }
    }

    private fun showProgress() = progressStatus(View.VISIBLE)

    private fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) {
            if (this is MainActivity) {
                this.showProgressStatus(viewStatus)
            }
        }
}
