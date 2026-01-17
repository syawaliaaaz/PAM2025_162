package com.example.sobatvet_20230140162.ui.screen.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sobatvet_20230140162.ui.components.CustomButton
import com.example.sobatvet_20230140162.ui.components.InputField
import com.example.sobatvet_20230140162.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    // Gunakan Scope untuk navigasi setelah registrasi berhasil (opsional)
    val registerSuccess = remember { mutableStateOf(false) }

    if (registerSuccess.value) {
        LaunchedEffect(Unit) {
            onNavigateToLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Daftar Akun",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Lengkapi data diri Anda",
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        InputField(
            value = name,
            onValueChange = { name = it },
            label = "Nama Lengkap"
        )

        InputField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )

        InputField(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = "Daftar Sekarang",
            onClick = {
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.register(name, email, password)
                    // Set ke true agar pindah ke halaman login setelah klik
                    registerSuccess.value = true
                }
            }
        )

        TextButton(onClick = onNavigateToLogin) {
            Text("Sudah punya akun? Login di sini")
        }
    }
}
