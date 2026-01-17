package com.example.sobatvet_20230140162.ui.screen.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.domain.model.Pet
import com.example.sobatvet_20230140162.ui.components.CustomButton
import com.example.sobatvet_20230140162.ui.components.InputField
import com.example.sobatvet_20230140162.viewmodel.BookingViewModel
import com.example.sobatvet_20230140162.viewmodel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    bookingViewModel: BookingViewModel,
    petViewModel: PetViewModel,
    userEmail: String,
    onNavigateBack: () -> Unit,
    onBookingSuccess: () -> Unit
) {
    val pets by petViewModel.pets.collectAsState()
    var selectedPet by remember { mutableStateOf<Pet?>(null) }
    var date by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(userEmail) {
        petViewModel.loadPets(userEmail)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Layanan") },
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
            Text(text = "Pilih Hewan:", style = MaterialTheme.typography.titleMedium)
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pets) { pet ->
                    FilterChip(
                        selected = selectedPet == pet,
                        onClick = { selectedPet = pet },
                        label = { Text(pet.name) }
                    )
                }
            }

            InputField(value = date, onValueChange = { date = it }, label = "Tanggal (DD/MM/YYYY)")
            InputField(value = notes, onValueChange = { notes = it }, label = "Catatan Keluhan")

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                text = "Konfirmasi Booking",
                onClick = {
                    selectedPet?.let {
                        val booking = Booking(
                            id = 0,
                            petId = it.id,
                            date = date,
                            status = "Pending",
                            notes = notes
                        )
                        bookingViewModel.createBooking(booking)
                        onBookingSuccess()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
