package com.example.sobatvet_20230140162.data.repository

import com.example.sobatvet_20230140162.data.local.dao.ReviewDao
import com.example.sobatvet_20230140162.data.local.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

class ReviewRepository(private val reviewDao: ReviewDao) {
    fun getReviewsByBooking(bookingId: Int): Flow<List<ReviewEntity>> = reviewDao.getReviewsByBooking(bookingId)
    suspend fun insertReview(review: ReviewEntity) = reviewDao.insertReview(review)
}
