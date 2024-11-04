package com.example.networkingapp.service

import com.example.networkingapp.model.requestMessageDto
import com.example.networkingapp.model.responseMessageDto
import com.example.networkingapp.networking.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.json.Json
import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import io.ktor.client.request.header
import io.ktor.client.request.request


class MessageService{

    private val client: HttpClient = createHttpClient()

    private fun HttpRequestBuilder.installBasicHeaders(){
        header("Accept", "application/json")
        header("User-Agent", "Networking App ")

    }



    suspend fun getMessages(url: String): responseMessageDto {
        val response: HttpResponse = client.get("")
        val messages: responseMessageDto = Json.decodeFromString(response.toString())
        return messages
    }

    @OptIn(InternalAPI::class)
    suspend fun sendMessage(url: String, message: responseMessageDto) {
        client.post(url) {

            installBasicHeaders()

            body = message
        }
    }

}