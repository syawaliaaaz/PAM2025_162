package com.example.sobatvet_20230140162.domain.usecase.booking

import com.example.sobatvet_20230140162.data.repository.BookingRepository

class CancelBookingUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(bookingId: Int) {
        repository.deleteBooking(bookingId)
    }
}
