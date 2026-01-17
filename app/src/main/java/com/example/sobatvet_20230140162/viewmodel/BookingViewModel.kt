package com.example.sobatvet_20230140162.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sobatvet_20230140162.domain.model.Booking
import com.example.sobatvet_20230140162.domain.usecase.booking.CancelBookingUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.CreateBookingUseCase
import com.example.sobatvet_20230140162.domain.usecase.booking.GetBookingHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel(
    private val getBookingHistoryUseCase: GetBookingHistoryUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase
) : ViewModel() {

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings

    init {
        loadBookings()
    }

    private fun loadBookings() {
        viewModelScope.launch {
            getBookingHistoryUseCase().collect {
                _bookings.value = it
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
}
