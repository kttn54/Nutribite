package com.example.sc_nutri

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileAPI {

    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    )

    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("http://ec2-52-90-201-98.compute-1.amazonaws.com:8000/")
                .build()
                .create(FileAPI::class.java)
        }
    }
}