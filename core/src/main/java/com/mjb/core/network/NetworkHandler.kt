package com.mjb.core.network

import android.content.Context
import com.mjb.core.extensions.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}
