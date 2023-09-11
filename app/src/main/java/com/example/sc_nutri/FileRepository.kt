package com.example.sc_nutri

import com.example.sc_nutri.models.RecommendationResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import java.io.File

class FileRepository {

    fun uploadProfileInfo(user: String): Call<String> =
        FileAPI.instance.uploadProfileInfo(user)

    fun uploadImages(ingredientsFile: File, nutritionalFile: File): Call<RecommendationResponse> {
        val requestBody1 = ingredientsFile.asRequestBody("image/jpg".toMediaTypeOrNull())
        val requestBody2 = nutritionalFile.asRequestBody("image/jpg".toMediaTypeOrNull())

        val imagePart1 = MultipartBody.Part.createFormData("ingredientsFile", ingredientsFile.name, requestBody1)
        val imagePart2 = MultipartBody.Part.createFormData("nutritionalFile", nutritionalFile.name, requestBody2)

        return FileAPI.instance.uploadImages(imagePart1, imagePart2)
    }
}