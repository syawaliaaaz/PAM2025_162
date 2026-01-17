package com.example.sobatvet_20230140162.domain.usecase.review

import com.example.sobatvet_20230140162.data.repository.ReviewRepository
import com.example.sobatvet_20230140162.domain.model.Review
import com.example.sobatvet_20230140162.data.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetReviewsUseCase(private val repository: ReviewRepository) {
    operator fun invoke(bookingId: Int): Flow<List<Review>> {
        // Assuming ReviewRepository has a method to get reviews by booking
        // For simplicity, let's use the current repository which I'll update if needed
        return repository.getReviewsByBooking(bookingId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
