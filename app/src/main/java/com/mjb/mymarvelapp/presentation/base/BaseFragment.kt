package com.mjb.mymarvelapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mjb.mymarvelapp.R
import com.mjb.mymarvelapp.core.AndroidApplication
import com.mjb.mymarvelapp.core.di.component.ViewComponent
import com.mjb.mymarvelapp.core.di.module.ViewModule
import com.mjb.mymarvelapp.presentation.MainActivity
import com.mjb.mymarvelapp.presentation.utils.extensions.showInfoAlertDialog

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

    protected fun showAlertDialog(failure: Throwable?) {
        showInfoAlertDialog {
            setTitle(getString(R.string.error_title))
            setText(failure?.message ?: getString(R.string.common_error))
            btnAccept {
                findNavController().navigateUp()
            }
        }.show()
    }
}
