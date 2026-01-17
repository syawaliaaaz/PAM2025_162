package com.example.sobatvet_20230140162.domain.usecase.review

import com.example.sobatvet_20230140162.data.repository.ReviewRepository
import com.example.sobatvet_20230140162.domain.model.Review
import com.example.sobatvet_20230140162.data.mapper.toEntity

class AddReviewUseCase(private val repository: ReviewRepository) {
    suspend operator fun invoke(review: Review) {
        repository.insertReview(review.toEntity())
    }
}
