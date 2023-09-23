package com.blissvine.swach.database

import com.blissvine.swach.models.RetroUser
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Authentication {
    @POST("/register")
    suspend fun Register(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/login")
    suspend fun Login(@Body requestBody: RequestBody): Response<ResponseBody>
}