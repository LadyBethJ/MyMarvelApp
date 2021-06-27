package com.mjb.core.network

import android.content.Context
import com.mjb.core.extensions.networkInfo

//import javax.inject.Inject

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}

//tampoco me sirve
/*interface NetworkHandler {
    val isConnected: Boolean
}

class NetworkHandlerImpl(private val context: Context) : NetworkHandler {
    override val isConnected get() = context.networkInfo
}*/

//TODO borrar dagger
/*class NetworkHandlerImpl
@Inject constructor(private val context: Context) : NetworkHandler {
    override val isConnected get() = context.networkInfo
}*/
