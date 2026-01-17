package com.example.sobatvet_20230140162.data.repository

import com.example.sobatvet_20230140162.data.local.dao.BookingDao
import com.example.sobatvet_20230140162.data.local.entity.BookingEntity
import kotlinx.coroutines.flow.Flow

class BookingRepository(private val bookingDao: BookingDao) {
    fun getAllBookings(): Flow<List<BookingEntity>> = bookingDao.getAllBookings()
    fun getBookingsByPet(petId: Int): Flow<List<BookingEntity>> = bookingDao.getBookingsByPet(petId)
    suspend fun getBookingById(id: Int): BookingEntity? = bookingDao.getBookingById(id)
    suspend fun insertBooking(booking: BookingEntity) = bookingDao.insertBooking(booking)
    suspend fun updateBooking(booking: BookingEntity) = bookingDao.updateBooking(booking)
    suspend fun deleteBooking(id: Int) = bookingDao.deleteBookingById(id)
}
