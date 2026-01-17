package com.example.sobatvet_20230140162.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sobatvet_20230140162.data.remote.RetrofitClient
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

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            try {
                // Memanggil Retrofit untuk login
                val response = RetrofitClient.instance.login(email, passwordHash)
                if (response.status == "success") {
                    val remoteUser = response.user
                    if (remoteUser != null) {
                        // Membuat objek User (Tanpa passwordHash karena di model domain User tidak ada field tersebut)
                        val user = User(
                            email = remoteUser.email,
                            name = remoteUser.name,
                            role = remoteUser.role
                        )
                        _loggedInUser.value = user
                        _isLoggedIn.value = true
                        _error.value = null
                    }
                } else {
                    _error.value = response.message
                    _isLoggedIn.value = false
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login Error: ${e.message}")
                _error.value = "Koneksi Gagal: Periksa IP atau Server"
                _isLoggedIn.value = false
            }
        }
    }

    fun register(name: String, email: String, passwordHash: String, role: String = "USER") {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.register(name, email, passwordHash, role)
                if (response.status == "success") {
                    _error.value = "Registrasi Berhasil, Silakan Login"
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Register Error: ${e.message}")
                _error.value = "Gagal terhubung ke server"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun logout() {
        _isLoggedIn.value = false
        _loggedInUser.value = null
    }
}
