package com.example.sobatvet_20230140162.ui.auth

import androidx.lifecycle.ViewModel

class AuthViewModel(private val repo: SobatVetRepository) : ViewModel() {
    var loginState by mutableStateOf<UserEntity?>(null)
    suspend fun login(email: String, password: String) {
        loginState = repo.login(email, password)
    }
    suspend fun register(email: String, password: String) {
        val hashed = password.hashCode().toString()
        repo.registerUser(UserEntity(email = email, passwordHash = hashed))
    }
}