package com.example.sc_nutri

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sc_nutri.Resource.*
import com.example.sc_nutri.models.ProfileResponse
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

    private val _profileInfo = MutableStateFlow<Resource<String>>(Unspecified())
    val profileInfo: Flow<Resource<String>> = _profileInfo

    private val _recommendationResponse = MutableStateFlow<Resource<RecommendationResponse>>(Unspecified())
    val recommendationResponse: Flow<Resource<RecommendationResponse>> = _recommendationResponse

    fun uploadProfileInfo(user: String) {
        repository.uploadProfileInfo(user).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null) {
                    viewModelScope.launch { _profileInfo.emit(Success(response.body()!!))}
                 } else {
                    viewModelScope.launch { _profileInfo.emit(Error("Response body is null")) }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                viewModelScope.launch { _profileInfo.emit(Error(t.message.toString()))}
            }
        })
    }

    fun uploadImages(ingredientsFile: File, nutritionalFile: File) {
        repository.uploadImages(ingredientsFile, nutritionalFile).enqueue(object: Callback<RecommendationResponse> {
            override fun onResponse(call: Call<RecommendationResponse>, response: Response<RecommendationResponse>) {
                if (response.body() != null) {
                    viewModelScope.launch {
                        _recommendationResponse.emit(Success(response.body()!!))
                    }
                } else {
                    viewModelScope.launch { _recommendationResponse.emit(Error("Response body is null")) }
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                viewModelScope.launch { _recommendationResponse.emit(Error(t.message.toString())) }
            }
        })
    }
}