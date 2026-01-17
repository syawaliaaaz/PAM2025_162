package com.example.sobatvet_20230140162.data.local.dao

import androidx.room.*
import com.example.sobatvet_20230140162.data.local.entity.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Query("SELECT * FROM pets WHERE ownerEmail = :email")
    fun getPetsByOwner(email: String): Flow<List<PetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: PetEntity)

    @Delete
    suspend fun deletePet(pet: PetEntity)
}
