package com.example.networkingapp.model

import kotlinx.serialization.Serializable

@Serializable
class SendMessageDto(
    var message: String? = null,
    var recipient: String? = null
)