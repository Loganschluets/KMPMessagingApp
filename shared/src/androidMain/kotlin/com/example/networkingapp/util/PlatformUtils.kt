package com.example.networkingapp.util
import java.util.*
//expect val OS = OS.IOS

actual fun randomUUID(): String = UUID.randomUUID().toString()

actual fun nativePrint(message: String, key: String){

}