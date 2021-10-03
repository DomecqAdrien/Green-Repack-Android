package com.esgi.greenrepack.services

import com.esgi.greenrepack.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {

    @POST("login")
    suspend fun login(@Body user: Utilisateur): Response<JwtResponse>

    @POST("association/login")
    suspend fun loginAsAssociation(@Body user: Utilisateur): Response<JwtResponse>

    @POST("association")
    suspend fun registerAssociation(@Body association: Association): Response<JwtResponse>

    @GET("associations")
    suspend fun getAllAssociations(@Header("Authorization") token: String): Response<List<Association>>

    @GET("association")
    suspend fun getAssociation(@Header("Authorization") token: String): Response<Association>

    @POST("projet")
    suspend fun createProjet(@Header("Authorization") token: String, @Body projet: Projet): Response<String>

    @GET("green_coin/user")
    suspend fun getGreenCoinsByUser(@Header("Authorization") token: String): Response<List<GreenCoin>>

    @POST("investment")
    suspend fun invest(@Header("Authorization") token: String, @Body investment: Investment): Response<String>

}