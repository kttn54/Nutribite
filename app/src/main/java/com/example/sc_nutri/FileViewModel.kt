package com.example.sc_nutri

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FileViewModel(
    private val repository: FileRepository = FileRepository()
): ViewModel() {

    fun uploadImage(file: File) {
        repository.uploadImage(file).enqueue(object: Callback<BackendResponse> {
            override fun onResponse(call: Call<BackendResponse>, response: Response<BackendResponse>) {
                if (response.body() != null) {
                    val response = response.body()!!.result
                    Log.d("test", "response from backend is $response")
                }
            }

            override fun onFailure(call: Call<BackendResponse>, t: Throwable) {
                Log.e("test", "error from backend: ${t.message.toString()}")
            }
        })
    }
}