package com.blissvine.swach.database

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface Authentication {
    @POST("/register")
    suspend fun Register(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/login")
    suspend fun Login(@Body requestBody: RequestBody): Response<ResponseBody>

//this is the photopost method
    @Multipart
    @POST("/photopost")
    suspend fun uploadAttachment( @Part("name") name: RequestBody, @Part("location") location: RequestBody,
                          @Part image: MultipartBody.Part): Response<ResponseBody>
}