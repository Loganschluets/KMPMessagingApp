package com.example.networkingapp.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    var sender: String? = null,
    var receiver: String? = null,
    var message: String? = null,
)