package com.example.sobatvet_20230140162.domain.usecase.booking

import com.example.sobatvet_20230140162.data.repository.BookingRepository
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.data.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBookingHistoryUseCase(private val repository: BookingRepository) {
    operator fun invoke(): Flow<List<Booking>> {
        return repository.getAllBookings().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
