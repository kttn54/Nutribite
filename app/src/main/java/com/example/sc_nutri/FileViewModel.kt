package com.example.sc_nutri

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sc_nutri.Resource.*
import com.example.sc_nutri.models.BackendResponse
import com.example.sc_nutri.models.RecommendationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FileViewModel(
    private val repository: FileRepository = FileRepository()
): ViewModel() {

    private val _profileInfo = MutableStateFlow<Resource<BackendResponse>>(Unspecified())
    val profileInfo: Flow<Resource<BackendResponse>> = _profileInfo

    fun uploadProfileInfo(user: String) {
        repository.uploadProfileInfo(user).enqueue(object: Callback<BackendResponse> {
            override fun onResponse(call: Call<BackendResponse>, response: Response<BackendResponse>) {
                if (response.body() != null) {
                    val responseBody = response.body()!!.result
                    viewModelScope.launch { _profileInfo.emit(Success(response.body()!!))}
                    Log.d("test", "profile info response from backend is $responseBody")
                } else {
                    Log.d("test", "profile info response???")
                }
            }

            override fun onFailure(call: Call<BackendResponse>, t: Throwable) {
                Log.e("test", "error from backend: ${t.message.toString()}")
            }
        })
    }

    fun uploadImage(file: File) {
        repository.uploadImage(file).enqueue(object: Callback<RecommendationResponse> {
            override fun onResponse(call: Call<RecommendationResponse>, response: Response<RecommendationResponse>) {
                if (response.body() != null) {
                    val response = response.body()!!.recommendation_response
                    Log.d("test", "response from backend is $response")
                } else {
                    Log.d("test", "response???")
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Log.e("test", "error from backend: ${t.message.toString()}")
            }
        })
    }
}