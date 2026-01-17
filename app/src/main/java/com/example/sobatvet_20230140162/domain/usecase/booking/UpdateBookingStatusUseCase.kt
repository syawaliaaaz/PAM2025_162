package com.example.sobatvet_20230140162.domain.usecase.booking

import com.example.sobatvet_20230140162.data.repository.BookingRepository

class UpdateBookingStatusUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(bookingId: Int, newStatus: String) {
        val booking = repository.getBookingById(bookingId)
        booking?.let {
            repository.updateBooking(it.copy(status = newStatus))
        }
    }
}
