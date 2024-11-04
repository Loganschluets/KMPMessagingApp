package com.example.networkingapp.networking

import io.ktor.client.HttpClient
import io.ktor.http.cio.Request
import kotlinx.coroutines.withContext
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC
/*
abstract class KtorRequest<T : Any>(displayAction: String?) : Request<T>(displayAction) {

    @Throws(Exception::class)
    override suspend fun request(): T = withContext(networkThread){
        return@withContext httpClient().use{
            request(it)
        }
    }

    @OptIn(ExperimentalObjCRefinement::class)
    @HiddenFromObjC
    @Throws(Exception::class)
    abstract suspend fun request(httpClient: HttpClient): T
}*/