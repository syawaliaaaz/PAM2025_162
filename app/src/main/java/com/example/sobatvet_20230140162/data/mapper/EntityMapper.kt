package com.example.sobatvet_20230140162.data.mapper

import com.example.sobatvet_20230140162.data.local.entity.BookingEntity
import com.example.sobatvet_20230140162.data.local.entity.PetEntity
import com.example.sobatvet_20230140162.data.local.entity.ReviewEntity
import com.example.sobatvet_20230140162.data.local.entity.UserEntity
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.domain.model.Pet
import com.example.sobatvet_20230140162.domain.model.Review
import com.example.sobatvet_20230140162.domain.model.User

fun UserEntity.toDomain() = User(email = email, name = name, role = role)
fun PetEntity.toDomain() = Pet(id, name, type, breed, age, ownerEmail)
fun BookingEntity.toDomain() = Booking(id, petId, date, status, notes)
fun ReviewEntity.toDomain() = Review(id, bookingId, rating, comment)

fun Pet.toEntity() = PetEntity(id, name, type, breed, age, ownerEmail)
fun Booking.toEntity() = BookingEntity(id, petId, date, status, notes)
fun Review.toEntity() = ReviewEntity(id, bookingId, rating, comment)
