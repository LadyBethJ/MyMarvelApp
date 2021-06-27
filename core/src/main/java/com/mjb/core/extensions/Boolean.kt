package com.mjb.core.extensions

fun Boolean?.orEmpty(): Boolean = this ?: false