package com.example.sobatvet_20230140162.ui.screen.pet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sobatvet_20230140162.domain.model.Pet
import com.example.sobatvet_20230140162.ui.components.CustomButton
import com.example.sobatvet_20230140162.ui.components.InputField
import com.example.sobatvet_20230140162.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetFormScreen(
    viewModel: PetViewModel,
    userEmail: String,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Hewan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            InputField(value = name, onValueChange = { name = it }, label = "Nama Hewan")
            InputField(value = type, onValueChange = { type = it }, label = "Jenis (Kucing/Anjing/dll)")
            InputField(value = breed, onValueChange = { breed = it }, label = "Ras")
            InputField(value = age, onValueChange = { age = it }, label = "Umur (Tahun)")

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                text = "Simpan",
                onClick = {
                    val pet = Pet(
                        id = 0,
                        name = name,
                        type = type,
                        breed = breed,
                        age = age.toIntOrNull() ?: 0,
                        ownerEmail = userEmail
                    )
                    viewModel.addPet(pet)
                    onNavigateBack()
                }
            )
        }
    }
}
