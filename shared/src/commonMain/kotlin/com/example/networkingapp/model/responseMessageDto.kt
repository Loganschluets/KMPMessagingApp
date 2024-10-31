package com.example.networkingapp.model

import kotlinx.serialization.Serializable

@Serializable
data class responseMessageDto(
    var message: String? = null,
    var sender: String? = null
)