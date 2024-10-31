package com.example.networkingapp.service

import com.example.networkingapp.model.requestMessageDto
import com.example.networkingapp.model.responseMessageDto
import com.example.networkingapp.networking.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.json.Json
import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType

class MessageService {

    private val client: HttpClient = createHttpClient()

    fun installBasicHeaders(){
    }

    suspend fun getMessages(url: String): responseMessageDto {
        val response: HttpResponse = client.get("")
        val messages: responseMessageDto = Json.decodeFromString(response.toString())
        return messages
    }

    @OptIn(InternalAPI::class)
    suspend fun sendMessage(url: String, message: responseMessageDto) {
        client.post(url) {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString()) // Content-Type
                append(HttpHeaders.Accept, ContentType.Application.Json.toString()) // Accept
                append(HttpHeaders.Authorization, "Bearer your_token_here") // Authorization (if needed)
                append(HttpHeaders.UserAgent, "YourAppName/1.0") // User-Agent
                // Add any custom headers as needed
                append("X-Custom-Header", "CustomHeaderValue")
            }


            body = message
        }
    }

}