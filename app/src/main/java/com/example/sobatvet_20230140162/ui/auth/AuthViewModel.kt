package com.example.sobatvet_20230140162.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sobatvet_20230140162.data.local.entity.UserEntity
import com.example.sobatvet_20230140162.data.repository.UserRepository

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    
    var loginState by mutableStateOf<UserEntity?>(null)
        private set

    suspend fun login(email: String, passwordHash: String) {
        val user = userRepository.getUserByEmail(email)
        if (user != null && user.passwordHash == passwordHash) {
            loginState = user
        } else {
            loginState = null
        }
    }

    suspend fun register(name: String, email: String, passwordHash: String) {
        val newUser = UserEntity(email = email, name = name, passwordHash = passwordHash)
        userRepository.insertUser(newUser)
    }
}
