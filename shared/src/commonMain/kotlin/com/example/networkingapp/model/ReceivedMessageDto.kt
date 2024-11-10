package com.example.networkingapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ReceivedMessageDto(
    var message: String? = null,
    var sender: String? = null
)