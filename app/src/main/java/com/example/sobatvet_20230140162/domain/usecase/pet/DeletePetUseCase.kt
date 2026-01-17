package com.example.sobatvet_20230140162.domain.usecase.pet

import com.example.sobatvet_20230140162.data.repository.PetRepository
import com.example.sobatvet_20230140162.domain.model.Pet
import com.example.sobatvet_20230140162.data.mapper.toEntity

class DeletePetUseCase(private val repository: PetRepository) {
    suspend operator fun invoke(pet: Pet) {
        repository.deletePet(pet.toEntity())
    }
}
