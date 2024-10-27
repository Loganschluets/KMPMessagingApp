package com.example.networkingapp.networking


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class HttpService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    // Function to make a GET request
    suspend fun <T> get(url: String, responseClass: Class<T>): T {
        return client.get(url).body()
    }

    // Function to make a POST request with a request body
    suspend fun <T> post(url: String, requestBody: Any, responseClass: Class<T>): T {
        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
            
        }.body()
    }

    // Function to make a POST request without expecting a specific response type
    suspend fun postWithoutResponse(url: String, requestBody: Any): HttpResponse {
        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    // Close the client to release resources
    fun close() {
        client.close()
    }
}