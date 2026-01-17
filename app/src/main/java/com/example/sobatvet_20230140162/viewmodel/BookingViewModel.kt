package com.example.sobatvet_20230140162.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sobatvet_20230140162.data.remote.RetrofitClient
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.domain.usecase.booking.CancelBookingUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.CreateBookingUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.GetBookingHistoryUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.UpdateBookingStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingViewModel(
    private val getBookingHistoryUseCase: GetBookingHistoryUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase,
    private val updateBookingStatusUseCase: UpdateBookingStatusUseCase
) : ViewModel() {

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()
    
    val allBookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    fun loadBookings(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getBookings(email)
                val mappedBookings = response.map { res ->
                    Booking(
                        id = res.id,
                        petId = 0,
                        date = res.date,
                        status = res.status,
                        notes = res.notes
                    )
                }
                _bookings.value = mappedBookings
            } catch (e: Exception) {
                Log.e("BookingViewModel", "Error loading bookings: ${e.message}")
            }
        }
    }

    fun loadAllBookingsForDoctor() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getBookings("ALL") 
                val mappedBookings = response.map { res ->
                    Booking(
                        id = res.id,
                        petId = 0,
                        date = res.date,
                        status = res.status,
                        notes = res.notes
                    )
                }
                _bookings.value = mappedBookings
            } catch (e: Exception) {
                Log.e("BookingViewModel", "Error loading all bookings: ${e.message}")
            }
        }
    }

    fun createBooking(petId: Int, date: String, notes: String, ownerEmail: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.addBooking(petId, date, notes)
                if (response.status == "success") {
                    loadBookings(ownerEmail)
                }
            } catch (e: Exception) {
                Log.e("BookingViewModel", "Error creating booking: ${e.message}")
            }
        }
    }

    fun updateBookingStatus(bookingId: Int, newStatus: String, callbackEmail: String = "ALL") {
        viewModelScope.launch {
            try {
                // Memanggil API update_booking_status.php
                val response = RetrofitClient.instance.updateBookingStatus(bookingId, newStatus)
                if (response.status == "success") {
                    // Refresh data setelah berhasil update
                    if (callbackEmail == "ALL") loadAllBookingsForDoctor() else loadBookings(callbackEmail)
                }
            } catch (e: Exception) {
                Log.e("BookingViewModel", "Error updating status: ${e.message}")
            }
        }
    }
}
