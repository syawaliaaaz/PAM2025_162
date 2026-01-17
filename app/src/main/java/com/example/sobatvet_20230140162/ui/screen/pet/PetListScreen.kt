package com.example.sobatvet_20230140162.ui.screen.pet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sobatvet_20230140162.ui.components.PetCard
import com.example.sobatvet_20230140162.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(
    viewModel: PetViewModel,
    userEmail: String,
    onNavigateBack: () -> Unit,
    onNavigateToAddPet: () -> Unit
) {
    val pets by viewModel.pets.collectAsState()

    LaunchedEffect(userEmail) {
        viewModel.loadPets(userEmail)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Hewan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddPet) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Hewan")
            }
        }
    ) { padding ->
        if (pets.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Belum ada hewan yang terdaftar")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pets) { pet ->
                    PetCard(pet = pet, onDelete = { viewModel.deletePet(pet) })
                }
            }
        }
    }
}
