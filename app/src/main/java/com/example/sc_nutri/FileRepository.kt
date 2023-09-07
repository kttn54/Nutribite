package com.example.sc_nutri

import com.example.sc_nutri.models.BackendResponse
import com.example.sc_nutri.models.RecommendationResponse
import com.example.sc_nutri.models.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import java.io.File

class FileRepository {

    fun uploadProfileInfo(user: String): Call<BackendResponse> =
        FileAPI.instance.uploadProfileInfo(user)

    fun uploadImage(file: File): Call<RecommendationResponse> =
        FileAPI.instance.uploadImage(
            image = MultipartBody.Part
                .createFormData(
                    "file",
                    file.name,
                    file.asRequestBody("image/jpg".toMediaTypeOrNull())
                )
        )
}