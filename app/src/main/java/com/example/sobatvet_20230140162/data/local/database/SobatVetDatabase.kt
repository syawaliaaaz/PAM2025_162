package com.example.sobatvet_20230140162.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sobatvet_20230140162.data.local.dao.BookingDao
import com.example.sobatvet_20230140162.data.local.dao.PetDao
import com.example.sobatvet_20230140162.data.local.dao.ReviewDao
import com.example.sobatvet_20230140162.data.local.dao.UserDao
import com.example.sobatvet_20230140162.data.local.entity.BookingEntity
import com.example.sobatvet_20230140162.data.local.entity.PetEntity
import com.example.sobatvet_20230140162.data.local.entity.ReviewEntity
import com.example.sobatvet_20230140162.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PetEntity::class,
        BookingEntity::class,
        ReviewEntity::class
    ],
    version = 1
)
abstract class SobatVetDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun petDao(): PetDao
    abstract fun bookingDao(): BookingDao
    abstract fun reviewDao(): ReviewDao
}
