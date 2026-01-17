package com.example.sobatvet_20230140162.data.local.dao

import androidx.room.*
import com.example.sobatvet_20230140162.data.local.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE bookingId = :bookingId")
    fun getReviewsByBooking(bookingId: Int): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewEntity)
}
