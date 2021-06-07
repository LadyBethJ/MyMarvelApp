package com.mjb.mymarvelapp.infrastructure.network

import android.content.Context
import com.mjb.mymarvelapp.presentation.utils.extensions.networkInfo
import javax.inject.Inject

interface NetworkHandler {
    val isConnected: Boolean
}

class NetworkHandlerImpl
@Inject constructor(private val context: Context) : NetworkHandler {
    override val isConnected get() = context.networkInfo
}
