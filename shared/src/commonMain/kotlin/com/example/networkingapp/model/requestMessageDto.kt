package com.example.networkingapp.model

import kotlinx.serialization.Serializable

@Serializable
class requestMessageDto(
    var message: String? = null,
    var recipient: String? = null
)