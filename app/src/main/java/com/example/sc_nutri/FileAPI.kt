package com.example.sc_nutri

import com.example.sc_nutri.models.BackendResponse
import com.example.sc_nutri.models.RecommendationResponse
import com.example.sc_nutri.models.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileAPI {

    @POST("profileInfo")
    fun uploadProfileInfo(
        @Body user: String
    ): Call<BackendResponse>

    @Multipart
    @POST("uploadImage")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<RecommendationResponse>

    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FileAPI::class.java)
        }
    }
}