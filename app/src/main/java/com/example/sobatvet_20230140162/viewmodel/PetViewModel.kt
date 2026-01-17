package com.example.sobatvet_20230140162.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sobatvet_20230140162.domain.model.Pet
import com.example.sobatvet_20230140162.domain.usecase.pet.AddPetUseCase
import com.example.sobatvet_20230140162.domain.usecase.pet.DeletePetUseCase
import com.example.sobatvet_20230140162.domain.usecase.pet.GetPetsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetViewModel(
    private val getPetsUseCase: GetPetsUseCase,
    private val addPetUseCase: AddPetUseCase,
    private val deletePetUseCase: DeletePetUseCase
) : ViewModel() {

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets

    fun loadPets(email: String) {
        viewModelScope.launch {
            getPetsUseCase(email).collect {
                _pets.value = it
            }
        }
    }

    fun addPet(pet: Pet) {
        viewModelScope.launch {
            addPetUseCase(pet)
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            deletePetUseCase(pet)
        }
    }
}
