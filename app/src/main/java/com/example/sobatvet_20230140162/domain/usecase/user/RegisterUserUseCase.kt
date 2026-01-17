package com.example.sobatvet_20230140162.domain.usecase.user

import com.example.sobatvet_20230140162.data.local.entity.UserEntity
import com.example.sobatvet_20230140162.data.repository.UserRepository

class RegisterUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(name: String, email: String, passwordHash: String): Boolean {
        val existingUser = repository.getUserByEmail(email)
        return if (existingUser == null) {
            repository.insertUser(UserEntity(email = email, name = name, passwordHash = passwordHash))
            true
        } else {
            false
        }
    }
}
