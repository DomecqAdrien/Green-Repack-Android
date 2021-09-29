package com.esgi.greenrepack.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.math.log

object ApiClient {

    private const val BASE_URL: String = "https://annualproject-back5.herokuapp.com/api/"
    private const val BB_URL: String = "https://api.imgbb.com/1/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val logging : HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
            HttpLoggingInterceptor.Level.BODY)
    }


    private val httpClient : OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            //.addInterceptor(logging)
            .build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val retrofitImage : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BB_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService : ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }

    val imageService: ImageService by lazy {
        retrofitImage.create(ImageService::class.java)
    }
}