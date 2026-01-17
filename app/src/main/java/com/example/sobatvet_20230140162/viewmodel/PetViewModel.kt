package com.example.sobatvet_20230140162.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sobatvet_20230140162.data.remote.RetrofitClient
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
            try {
                val response = RetrofitClient.instance.getPets(email)
                val mappedPets = response.map { res ->
                    Pet(
                        id = res.id,
                        name = res.name,
                        type = res.type,
                        breed = res.breed,
                        age = res.age,
                        ownerEmail = res.ownerEmail
                    )
                }
                _pets.value = mappedPets
            } catch (e: Exception) {
                Log.e("PetViewModel", "Error loading pets: ${e.message}")
            }
        }
    }

    fun addPet(pet: Pet) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addPet(
                    name = pet.name,
                    type = pet.type,
                    breed = pet.breed,
                    age = pet.age,
                    ownerEmail = pet.ownerEmail
                )
                if (response.status == "success") {
                    loadPets(pet.ownerEmail)
                }
            } catch (e: Exception) {
                Log.e("PetViewModel", "Error adding pet: ${e.message}")
            }
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch {
            // Untuk delete, Anda bisa menambahkan @DELETE di ApiService jika diperlukan
            // Sementara ini kita hapus dari state lokal atau panggil delete API jika sudah ada
            deletePetUseCase(pet)
        }
    }
}
