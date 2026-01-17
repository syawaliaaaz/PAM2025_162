package com.example.sobatvet_20230140162.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sobatvet_20230140162.domain.model.User
import com.example.sobatvet_20230140162.domain.usecase.user.LoginUserUseCase
import com.example.sobatvet_20230140162.domain.usecase.user.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUserUseCase,
    private val registerUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser.asStateFlow()

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            val user = loginUseCase(email, passwordHash)
            _isLoggedIn.value = user != null
            _loggedInUser.value = user
        }
    }

    fun register(name: String, email: String, passwordHash: String) {
        viewModelScope.launch {
            registerUseCase(name, email, passwordHash)
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _loggedInUser.value = null
    }
}
