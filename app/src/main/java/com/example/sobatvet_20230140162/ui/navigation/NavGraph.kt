package com.example.sobatvet_20230140162.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sobatvet_20230140162.domain.model.User
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

sealed class Screen(val route: String, val title: String = "", val icon: @Composable (() -> Unit)? = null) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    
    // Bottom Nav Items
    object Home : Screen("home", "Home", { Icon(Icons.Default.Home, contentDescription = null) })
    object Booking : Screen("booking", "Booking", { Icon(Icons.Default.DateRange, contentDescription = null) })
    object PetList : Screen("pet_list", "Pets", { Icon(Icons.Default.List, contentDescription = null) })
    object History : Screen("history", "History", { Icon(Icons.Default.DateRange, contentDescription = null) })
    
    object PetForm : Screen("pet_form")
    object Profile : Screen("profile", "Profile", { Icon(Icons.Default.Person, contentDescription = null) })
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    petViewModel: PetViewModel,
    bookingViewModel: BookingViewModel
) {
    // Memberikan tipe data eksplisit pada collectAsState untuk menghindari error inference
    val loggedInUser by authViewModel.loggedInUser.collectAsState(initial = null)
    val userEmail = loggedInUser?.email ?: "user@example.com"
    val userName = loggedInUser?.name ?: "Pemilik Hewan"

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Booking,
        Screen.PetList,
        Screen.History
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route } || currentRoute == Screen.Profile.route) {
                NavigationBar {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = screen.icon ?: {},
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                    NavigationBarItem(
                        icon = Screen.Profile.icon ?: {},
                        label = { Text(Screen.Profile.title) },
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
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
                    userName = userName,
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
                    onNavigateToReview = { /* Implement Review */ }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    userEmail = userEmail,
                    onNavigateBack = { navController.popBackStack() },
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
