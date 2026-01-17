package com.example.sobatvet_20230140162.domain.usecase.user

import com.example.sobatvet_20230140162.data.repository.UserRepository
import com.example.sobatvet_20230140162.domain.model.User
import com.example.sobatvet_20230140162.data.mapper.toDomain

class LoginUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, passwordHash: String): User? {
        val userEntity = repository.getUserByEmail(email)
        return if (userEntity != null && userEntity.passwordHash == passwordHash) {
            userEntity.toDomain()
        } else {
            null
        }
    }
}
