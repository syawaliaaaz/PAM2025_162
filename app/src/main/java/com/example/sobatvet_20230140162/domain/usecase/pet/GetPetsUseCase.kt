package com.example.sobatvet_20230140162.domain.usecase.pet

import com.example.sobatvet_20230140162.data.repository.PetRepository
import com.example.sobatvet_20230140162.domain.model.Pet
import com.example.sobatvet_20230140162.data.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPetsUseCase(private val repository: PetRepository) {
    operator fun invoke(email: String): Flow<List<Pet>> {
        return repository.getPetsByOwner(email).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
