package com.example.sc_nutri

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class FileRepository {

    fun uploadImage(file: File): Call<BackendResponse> =
        FileAPI.instance.uploadImage(
            image = MultipartBody.Part
                .createFormData(
                    "file",
                    file.name,
                    file.asRequestBody()
                )
        )
}