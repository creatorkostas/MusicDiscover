package com.musicdiscover.auth.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel() : ViewModel() {
    var text: String by mutableStateOf("")
//    var email: String by mutableStateOf("")
//    var password: String by mutableStateOf("")
//    var token: String by mutableStateOf("")
}

