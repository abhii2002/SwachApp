package com.blissvine.swach.network


import com.blissvine.swach.models.GuideLinesModel
import com.blissvine.swach.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    @GET("get-guideline")
   suspend fun fetchGuidelines() : Response<List<GuideLinesModel>>


}