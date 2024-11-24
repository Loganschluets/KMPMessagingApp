package com.example.networkingapp.util
import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()

actual fun nativePrint(message: String, key: String){

}