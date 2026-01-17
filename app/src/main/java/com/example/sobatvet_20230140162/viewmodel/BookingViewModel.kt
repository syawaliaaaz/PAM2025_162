package com.example.sobatvet_20230140162.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _allBookings = MutableStateFlow<List<Booking>>(emptyList())
    val allBookings: StateFlow<List<Booking>> = _allBookings.asStateFlow()

    init {
        loadBookings()
    }

    private fun loadBookings() {
        viewModelScope.launch {
            getBookingHistoryUseCase().collect {
                _bookings.value = it
                _allBookings.value = it
            }
        }
    }

    fun createBooking(booking: Booking) {
        viewModelScope.launch {
            createBookingUseCase(booking)
        }
    }

    fun cancelBooking(bookingId: Int) {
        viewModelScope.launch {
            cancelBookingUseCase(bookingId)
        }
    }

    fun updateBookingStatus(bookingId: Int, newStatus: String) {
        viewModelScope.launch {
            updateBookingStatusUseCase(bookingId, newStatus)
            loadBookings() // Refresh data
        }
    }
}
