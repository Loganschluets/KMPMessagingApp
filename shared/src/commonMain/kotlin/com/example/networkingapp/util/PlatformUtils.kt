package com.example.networkingapp.util

enum class OS{
    Android,
    IOS
}

//expect val OS

expect fun nativePrint(message: String, key: String)