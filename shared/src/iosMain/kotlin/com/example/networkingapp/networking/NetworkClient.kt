package com.example.networkingapp.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

actual fun createHttpClient(): HttpClient {
    return HttpClient(Darwin) {
        // Configure client-specific settings here, if needed
        install(ContentNegotiation) {
            json() // Automatically configure the JSON serializer
        }

        // Configure timeout settings
        engine {
            configureRequest {
                timeoutIntervalForRequest = 15.0
                timeoutIntervalForResource = 15.0
            }
        }
    }
}