package com.example.networkingapp.viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    fun sendHttpRequest(){
        viewModelScope.launch{
            kotlin.runCatching {
                //TODO: handle request

            }.onFailure {


            }.onSuccess {

            }
        }
    }
}