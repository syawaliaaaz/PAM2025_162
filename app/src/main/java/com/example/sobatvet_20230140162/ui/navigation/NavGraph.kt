package com.example.sobatvet_20230140162.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sobatvet_20230140162.ui.auth.LoginScreen
import com.example.sobatvet_20230140162.ui.screen.auth.RegisterScreen
import com.example.sobatvet_20230140162.ui.screen.home.HomeScreen
import com.example.sobatvet_20230140162.ui.screen.pet.PetListScreen
import com.example.sobatvet_20230140162.ui.screen.pet.PetFormScreen
import com.example.sobatvet_20230140162.ui.screen.booking.BookingScreen
import com.example.sobatvet_20230140162.ui.screen.history.HistoryScreen
import com.example.sobatvet_20230140162.ui.screen.profile.ProfileScreen
import com.example.sobatvet_20230140162.ui.screen.splash.SplashScreen
import com.example.sobatvet_20230140162.viewmodel.AuthViewModel
import com.example.sobatvet_20230140162.viewmodel.BookingViewModel
import com.example.sobatvet_20230140162.viewmodel.PetViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object PetList : Screen("pet_list")
    object PetForm : Screen("pet_form")
    object Booking : Screen("booking")
    object History : Screen("history")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    petViewModel: PetViewModel,
    bookingViewModel: BookingViewModel
) {
    // Simulasi email user yang login (sebaiknya disimpan di session/datastore)
    val userEmail = "user@example.com"

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPetList = { navController.navigate(Screen.PetList.route) },
                onNavigateToBooking = { navController.navigate(Screen.Booking.route) },
                onNavigateToHistory = { navController.navigate(Screen.History.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) }
            )
        }
        composable(Screen.PetList.route) {
            PetListScreen(
                viewModel = petViewModel,
                userEmail = userEmail,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddPet = { navController.navigate(Screen.PetForm.route) }
            )
        }
        composable(Screen.PetForm.route) {
            PetFormScreen(
                viewModel = petViewModel,
                userEmail = userEmail,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Booking.route) {
            BookingScreen(
                bookingViewModel = bookingViewModel,
                petViewModel = petViewModel,
                userEmail = userEmail,
                onNavigateBack = { navController.popBackStack() },
                onBookingSuccess = { navController.navigate(Screen.History.route) }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = bookingViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToReview = { /* Implementasi Review Screen */ }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                userEmail = userEmail,
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
