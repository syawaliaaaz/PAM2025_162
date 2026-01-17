package com.example.sobatvet_20230140162.data.remote.response

data class PetResponse(
    val id: Int,
    val name: String,
    val type: String,
    val breed: String,
    val age: Int,
    val ownerEmail: String
)

data class BookingResponse(
    val id: Int,
    val petName: String,
    val date: String,
    val status: String,
    val notes: String
)

data class BaseResponse(
    val status: String,
    val message: String
)
