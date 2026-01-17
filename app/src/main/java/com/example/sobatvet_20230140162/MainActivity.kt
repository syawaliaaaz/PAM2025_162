package com.example.sobatvet_20230140162

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.sobatvet_20230140162.data.local.database.SobatVetDatabase
import com.example.sobatvet_20230140162.data.repository.BookingRepository
import com.example.sobatvet_20230140162.data.repository.PetRepository
import com.example.sobatvet_20230140162.data.repository.UserRepository
import com.example.sobatvet_20230140162.domain.usecase.user.LoginUserUseCase
import com.example.sobatvet_20230140162.domain.usecase.user.RegisterUserUseCase
import com.example.sobatvet_20230140162.domain.usecase.pet.AddPetUseCase
import com.example.sobatvet_20230140162.domain.usecase.pet.DeletePetUseCase
import com.example.sobatvet_20230140162.domain.usecase.pet.GetPetsUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.CancelBookingUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.CreateBookingUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.GetBookingHistoryUseCase
import com.example.sobatvet_20230140162.ui.navigation.NavGraph
import com.example.sobatvet_20230140162.ui.theme.SobatVet_20230140162Theme
import com.example.sobatvet_20230140162.viewmodel.AuthViewModel
import com.example.sobatvet_20230140162.viewmodel.BookingViewModel
import com.example.sobatvet_20230140162.viewmodel.PetViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Inisialisasi Database (Sangat sederhana, untuk produksi gunakan Dependency Injection seperti Hilt)
        val db = Room.databaseBuilder(
            applicationContext,
            SobatVetDatabase::class.java, "sobatvet-db"
        ).build()

        // Repositories
        val userRepository = UserRepository(db.userDao())
        val petRepository = PetRepository(db.petDao())
        val bookingRepository = BookingRepository(db.bookingDao())

        // UseCases
        val loginUseCase = LoginUserUseCase(userRepository)
        val registerUseCase = RegisterUserUseCase(userRepository)
        val getPetsUseCase = GetPetsUseCase(petRepository)
        val addPetUseCase = AddPetUseCase(petRepository)
        val deletePetUseCase = DeletePetUseCase(petRepository)
        val getBookingHistoryUseCase = GetBookingHistoryUseCase(bookingRepository)
        val createBookingUseCase = CreateBookingUseCase(bookingRepository)
        val cancelBookingUseCase = CancelBookingUseCase(bookingRepository)

        // ViewModels
        val authViewModel = AuthViewModel(loginUseCase, registerUseCase)
        val petViewModel = PetViewModel(getPetsUseCase, addPetUseCase, deletePetUseCase)
        val bookingViewModel = BookingViewModel(getBookingHistoryUseCase, createBookingUseCase, cancelBookingUseCase)

        setContent {
            SobatVet_20230140162Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        petViewModel = petViewModel,
                        bookingViewModel = bookingViewModel
                    )
                }
            }
        }
    }
}
