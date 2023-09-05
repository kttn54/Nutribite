package com.example.sc_nutri

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileAPI {

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<BackendResponse>

    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FileAPI::class.java)
        }
    }
}