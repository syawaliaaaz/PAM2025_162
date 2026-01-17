package com.example.sobatvet_20230140162.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val passwordHash: String,
    val role: String = "USER" // Tambahan: USER atau DOCTOR
)
