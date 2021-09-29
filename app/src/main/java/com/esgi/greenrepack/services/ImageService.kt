package com.esgi.greenrepack.services

import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageService {

    @POST("upload?key=664ba998e87ee0c96fa1a3efa7f277c7")
    suspend fun upload(@Body data: RequestBody): Response<JsonObject>
}