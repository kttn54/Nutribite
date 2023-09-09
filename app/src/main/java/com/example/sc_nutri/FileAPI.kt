package com.example.sc_nutri

import com.example.sc_nutri.models.ProfileResponse
import com.example.sc_nutri.models.RecommendationResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

interface FileAPI {

    @POST("profile-info")
    fun uploadProfileInfo(
        @Body user: String
    ): Call<String>

    @Multipart
    @POST("food-recommendation")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<RecommendationResponse>

    companion object {
        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout to 30 seconds
            .readTimeout(30, TimeUnit.SECONDS)    // Set the read timeout to 30 seconds
            .build()

        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4000/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FileAPI::class.java)
        }
    }
}