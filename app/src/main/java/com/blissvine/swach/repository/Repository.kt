package com.blissvine.swach.repository

import com.blissvine.swach.models.GuideLinesModel
import com.blissvine.swach.network.ApiService
import retrofit2.Response

class Repository(private val apiService: ApiService) {


    suspend fun fetchGuidlines() : Response<List<GuideLinesModel>> = apiService.fetchGuidelines()
}