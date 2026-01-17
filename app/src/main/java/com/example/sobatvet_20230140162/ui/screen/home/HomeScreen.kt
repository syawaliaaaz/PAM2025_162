package com.example.sobatvet_20230140162.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPetList: () -> Unit,
    onNavigateToBooking: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("SobatVet") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToPetList) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Hewan")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNavigateToPetList
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Daftar Hewan Peliharaan", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Kelola hewan kesayangan Anda")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNavigateToBooking
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Booking Layanan", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Jadwalkan konsultasi atau perawatan")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNavigateToHistory
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Riwayat & Review", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Lihat aktivitas sebelumnya")
                }
            }
            
            Button(
                onClick = onNavigateToProfile,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Profil Saya")
            }
        }
    }
}
