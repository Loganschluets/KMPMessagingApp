package com.example.networkingapp.service

import com.example.networkingapp.model.MessageDto
import com.example.networkingapp.networking.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.contentType

object MessageService {

    private val client: HttpClient = createHttpClient()

    private fun HttpRequestBuilder.installBasicHeaders() {
        header("Accept", "application/json")
        header("User-Agent", "Networking App")
    }

    suspend fun getMessagesForUser(user: String): Array<MessageDto> {
        val url = "http://52.90.153.18:80/getMessages?receiver=$user"
        return client.get(url) {
            installBasicHeaders()
            contentType(ContentType.Application.Json)
        }.body()
    }

    suspend fun clearMessagesForUser(recipient: String) {
        val url = "http://52.90.153.18:80/deleteMessages?recipient=$recipient"
        client.request(url) {
            method = HttpMethod.Delete
            installBasicHeaders()
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun sendMessageFromUser(message: MessageDto) {
        val url = "http://52.90.153.18:80/sendMessage"
        client.post(url) {
            installBasicHeaders()
            contentType(ContentType.Application.Json)
            setBody(message)
        }
    }


}
