package com.example.sobatvet_20230140162.data.remote

import com.example.sobatvet_20230140162.data.remote.response.*
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register.php")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): LoginResponse

    // PETS
    @GET("get_pets.php")
    suspend fun getPets(@Query("ownerEmail") ownerEmail: String): List<PetResponse>

    @FormUrlEncoded
    @POST("add_pet.php")
    suspend fun addPet(
        @Field("name") name: String,
        @Field("type") type: String,
        @Field("breed") breed: String,
        @Field("age") age: Int,
        @Field("ownerEmail") ownerEmail: String
    ): BaseResponse

    // BOOKING
    @FormUrlEncoded
    @POST("add_booking.php")
    suspend fun addBooking(
        @Field("petId") petId: Int,
        @Field("date") date: String,
        @Field("notes") notes: String
    ): BaseResponse

    @GET("get_bookings.php")
    suspend fun getBookings(@Query("ownerEmail") ownerEmail: String): List<BookingResponse>
}
