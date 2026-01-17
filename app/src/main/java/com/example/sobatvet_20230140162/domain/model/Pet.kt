package com.example.sobatvet_20230140162.domain.model

data class Pet(
    val id: Int,
    val name: String,
    val type: String,
    val breed: String,
    val age: Int,
    val ownerEmail: String
)
