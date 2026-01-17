package com.example.sobatvet_20230140162.ui.screen.doctor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.viewmodel.BookingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDashboardScreen(
    bookingViewModel: BookingViewModel,
    onLogout: () -> Unit
) {
    val allBookings by bookingViewModel.allBookings.collectAsState()

    // Memuat semua data booking saat layar dibuka
    LaunchedEffect(Unit) {
        bookingViewModel.loadAllBookingsForDoctor()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Dokter") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
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
            Text(
                text = "Antrean Booking Masuk",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Menampilkan yang statusnya Pending
            val pendingBookings = allBookings.filter { it.status == "Pending" }

            if (pendingBookings.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada booking masuk.")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pendingBookings) { booking ->
                        BookingItemDoctor(
                            booking = booking,
                            onApprove = { bookingViewModel.updateBookingStatus(booking.id, "Approved") },
                            onReject = { bookingViewModel.updateBookingStatus(booking.id, "Rejected") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookingItemDoctor(
    booking: Booking,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Tanggal: ${booking.date}", fontWeight = FontWeight.Bold)
            Text(text = booking.notes, style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onReject,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Tolak")
                }
                
                Button(
                    onClick = onApprove,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Terima")
                }
            }
        }
    }
}
