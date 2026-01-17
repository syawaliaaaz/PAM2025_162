package com.example.sobatvet_20230140162.data.repository

import com.example.sobatvet_20230140162.data.local.dao.PetDao
import com.example.sobatvet_20230140162.data.local.entity.PetEntity
import kotlinx.coroutines.flow.Flow

class PetRepository(private val petDao: PetDao) {
    fun getPetsByOwner(email: String): Flow<List<PetEntity>> = petDao.getPetsByOwner(email)
    suspend fun insertPet(pet: PetEntity) = petDao.insertPet(pet)
    suspend fun deletePet(pet: PetEntity) = petDao.deletePet(pet)
}
