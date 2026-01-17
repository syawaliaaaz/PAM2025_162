package com.example.sobatvet_20230140162.data.remote.response

data class LoginResponse(
    val status: String,
    val message: String,
    val user: UserRemote?
)

data class UserRemote(
    val name: String,
    val email: String,
    val role: String
)
