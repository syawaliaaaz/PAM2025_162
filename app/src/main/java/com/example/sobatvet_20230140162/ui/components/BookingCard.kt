package com.example.sobatvet_20230140162.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sobatvet_20230140162.domain.model.Booking

@Composable
fun BookingCard(
    booking: Booking,
    onAddReview: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Booking #${booking.id}", style = MaterialTheme.typography.titleMedium)
                Surface(
                    color = if (booking.status == "Selesai") Color(0xFF4CAF50) else Color(0xFFFF9800),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = booking.status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Text(text = "Tanggal: ${booking.date}", modifier = Modifier.padding(top = 4.dp))
            Text(text = "Keluhan: ${booking.notes}", style = MaterialTheme.typography.bodySmall)

            if (booking.status == "Selesai") {
                Button(
                    onClick = onAddReview,
                    modifier = Modifier.padding(top = 8.dp).align(Alignment.End)
                ) {
                    Text("Beri Review")
                }
            }
        }
    }
}
