package com.example.sobatvet_20230140162.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val petId: Int,
    val date: String,
    val status: String, // Pending, Approved, Rejected
    val notes: String
)
