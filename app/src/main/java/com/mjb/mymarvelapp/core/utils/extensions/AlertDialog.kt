package com.mjb.mymarvelapp.presentation.utils.extensions

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mjb.mymarvelapp.core.utils.dialogs.InfoAlertDialog

inline fun Fragment.showInfoAlertDialog(func: InfoAlertDialog.() -> Unit): AlertDialog =
    InfoAlertDialog(this.requireContext()).apply {
        func()
    }.create()
