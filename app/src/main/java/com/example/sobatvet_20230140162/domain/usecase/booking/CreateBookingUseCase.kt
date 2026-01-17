package com.example.sobatvet_20230140162.domain.usecase.booking

import com.example.sobatvet_20230140162.data.repository.BookingRepository
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.data.mapper.toEntity

class CreateBookingUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(booking: Booking) {
        repository.insertBooking(booking.toEntity())
    }
}
