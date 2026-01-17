package com.example.sobatvet_20230140162.ui.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userEmail: String,
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showReviewDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Dialog 1: Konfirmasi Logout
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Konfirmasi Logout") },
            text = { Text("Apakah Anda yakin ingin keluar dari akun?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    showReviewDialog = true // Setelah klik Yes, munculkan dialog review
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    // Dialog 2: Tawaran Review (Praktek Drh. Endang Setianingsih)
    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { 
                showReviewDialog = false
                onLogout() 
            },
            title = { Text("Beri Ulasan") },
            text = { Text("Terima kasih sudah menggunakan layanan kami. Apakah Anda ingin memberikan ulasan untuk Praktek Drh. Endang Setianingsih di Google Maps?") },
            confirmButton = {
                TextButton(onClick = {
                    showReviewDialog = false
                    // Buka Google Maps langsung ke lokasi praktek mama Anda di Jambi
                    val alamatKlinik = "Praktek Drh. Endang Setianingsih, Jambi"
                    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(alamatKlinik)}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                    
                    onLogout() // Tetap logout setelah diarahkan ke Maps
                }) {
                    Text("Iya")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showReviewDialog = false
                    onLogout() // Logout langsung jika pilih Nanti
                }) {
                    Text("Nanti")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil") },
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = "Email Akun:", style = MaterialTheme.typography.labelLarge)
            Text(text = userEmail, style = MaterialTheme.typography.titleLarge)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            HorizontalDivider()
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout")
                }
            }
        }
    }
}
