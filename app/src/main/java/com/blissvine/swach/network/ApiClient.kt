package com.blissvine.swach.network

import com.blissvine.swach.models.PhotoData
import com.blissvine.swach.models.UserDataResponse
import com.blissvine.swach.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

object ApiClient {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}


interface ApiService {

    @POST("/photopost")
    suspend fun photopost(@Body photoData: PhotoData): Response<PhotoData>


    @GET("/fetch")
    suspend fun getUserData() : Response<List<UserDataResponse>>



}