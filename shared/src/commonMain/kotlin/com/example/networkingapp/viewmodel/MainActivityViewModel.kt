package com.example.networkingapp.viewmodel

import com.example.networkingapp.model.MessageDto
import com.example.networkingapp.model.ReceivedMessageDto
import com.example.networkingapp.model.SendMessageDto
import com.example.networkingapp.service.MessageService
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import kotlinx.coroutines.launch

class MainActivityViewModel() : ViewModel() {

    private val mMessageList: MutableLiveData<MutableList<MessageDto>> = MutableLiveData(
        mutableListOf()
    )
    val messageList: LiveData<MutableList<MessageDto>> = mMessageList

    fun sendMessage(sender: String, receiver: String, messageText: String){
        viewModelScope.launch{
            kotlin.runCatching {
                val message = MessageDto(sender, receiver, messageText)
                MessageService.sendMessageFromUser(message)

            }.onFailure {
                println("sendMessage failed with error: ${it.message}")
            }.onSuccess {
                println("sendMessage succeeded") // Success log
            }
        }
    }

    fun fetchMessages(user: String){
        viewModelScope.launch{
            kotlin.runCatching {
                val response: Array<MessageDto> = MessageService.getMessagesForUser(user)
                mMessageList.value = response.toMutableList()


            }.onFailure {
                println("fetchMessages failed with error: ${it.message}\"")

            }.onSuccess {
                println("fetchMessages succeeded") // Success log
            }
        }
    }
}