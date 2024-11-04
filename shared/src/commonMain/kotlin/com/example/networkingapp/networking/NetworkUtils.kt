package com.example.networkingapp.networking

import kotlinx.coroutines.CoroutineDispatcher

expect val networkThread: CoroutineDispatcher

//expect val isOnline: Boolean

//expect val Throwable.isNoNetworkConnectionError : Boolean