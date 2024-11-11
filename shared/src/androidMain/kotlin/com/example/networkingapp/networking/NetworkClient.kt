package com.example.networkingapp.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

actual fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        // Configure client-specific settings here, if needed
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // This will ignore unknown fields in the JSON response
                isLenient = true         // Optional: allows lenient parsing of JSON
            })
        }


        engine {
            config {
                connectTimeout(15, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
                writeTimeout(15, TimeUnit.SECONDS)
            }
        }

    }
}