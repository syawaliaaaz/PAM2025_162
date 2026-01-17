package com.example.sobatvet_20230140162.domain.model

data class Booking(
    val id: Int,
    val petId: Int,
    val date: String,
    val status: String,
    val notes: String
)
