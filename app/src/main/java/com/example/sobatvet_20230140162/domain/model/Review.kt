package com.example.sobatvet_20230140162.domain.model

data class Review(
    val id: Int,
    val bookingId: Int,
    val rating: Int,
    val comment: String
)
