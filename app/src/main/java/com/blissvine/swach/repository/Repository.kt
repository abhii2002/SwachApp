package com.blissvine.swach.repository

import com.blissvine.swach.models.PhotoData
import com.blissvine.swach.models.UserDataResponse
import com.blissvine.swach.network.ApiService
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class Repository(private val apiService: ApiService) {
    suspend fun postPhoto(photoData: PhotoData) : Response<PhotoData> =  apiService.photopost(photoData)

    suspend fun getUserDetails() : Response<List<UserDataResponse>> = apiService.getUserData()
}