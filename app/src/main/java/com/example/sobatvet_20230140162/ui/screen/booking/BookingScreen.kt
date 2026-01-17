package com.example.sobatvet_20230140162.ui.screen.booking

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.domain.model.Pet
import com.example.sobatvet_20230140162.ui.components.CustomButton
import com.example.sobatvet_20230140162.viewmodel.BookingViewModel
import com.example.sobatvet_20230140162.viewmodel.PetViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
    var selectedDoctor by remember { mutableStateOf("Drh. Endang Setianingsih") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    
    val selectedDateText = datePickerState.selectedDateMillis?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
    } ?: "Pilih Tanggal"

    LaunchedEffect(userEmail) {
        petViewModel.loadPets(userEmail)
    }

    // Fungsi untuk membuka Google Maps dengan Tanda (Marker)
    val openMaps = {
        // Alamat klinik di Jambi
        val alamatKlinik = "Klinik Hewan Drh. Endang Setianingsih, Jambi" 
        val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(alamatKlinik)}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Batal")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Informasi Dokter Tunggal
            Text(text = "Dokter Hewan:", style = MaterialTheme.typography.titleMedium)
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputChip(
                    selected = true,
                    onClick = { },
                    label = { Text(selectedDoctor) },
                    leadingIcon = { 
                        Icon(
                            Icons.Default.Person, 
                            contentDescription = null, 
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        ) 
                    }
                )

                // Tombol Lokasi (Marker)
                TextButton(onClick = { openMaps() }) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Lihat Lokasi")
                }
            }

            Text(text = "Pilih Hewan Peliharaan:", style = MaterialTheme.typography.titleMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(pets) { pet ->
                    FilterChip(
                        selected = selectedPet?.id == pet.id,
                        onClick = { selectedPet = pet },
                        label = { Text(pet.name) }
                    )
                }
            }

            Text(text = "Pilih Tanggal:", style = MaterialTheme.typography.titleMedium)
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = selectedDateText)
                    Icon(Icons.Default.DateRange, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                text = "Konfirmasi Booking",
                onClick = {
                    if (selectedPet == null) {
                        scope.launch { snackbarHostState.showSnackbar("Silakan pilih hewan") }
                        return@CustomButton
                    }
                    if (datePickerState.selectedDateMillis == null) {
                        scope.launch { snackbarHostState.showSnackbar("Silakan pilih tanggal") }
                        return@CustomButton
                    }
                    
                    selectedPet?.let {
                        val booking = Booking(
                            id = 0,
                            petId = it.id,
                            date = selectedDateText,
                            status = "Pending",
                            notes = "Dokter: $selectedDoctor | Pasien: ${it.name}"
                        )
                        bookingViewModel.createBooking(booking)
                        
                        scope.launch {
                            snackbarHostState.showSnackbar("Booking Berhasil! Menunggu konfirmasi dokter.")
                        }
                        onBookingSuccess()
                    }
                }
            )
        }
    }
}
