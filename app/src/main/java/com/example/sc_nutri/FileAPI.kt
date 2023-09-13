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
    fun uploadImages(
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part
    ): Call<RecommendationResponse>

    companion object {
        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("http://192.168.43.15:4000/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FileAPI::class.java)
        }
    }
}