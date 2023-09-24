package com.blissvine.swach.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blissvine.swach.models.PhotoData
import com.blissvine.swach.models.UserDataResponse
import com.blissvine.swach.network.ApiClient
import com.blissvine.swach.repository.Repository
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response
import java.lang.Exception


class MainViewModel(private val repository: Repository =  Repository(ApiClient.apiService)): ViewModel() {

    var myResponse: MutableLiveData<Response<PhotoData>> = MutableLiveData()
    var userDetailsLiveData : MutableLiveData<Response<List<UserDataResponse>>> = MutableLiveData()


    fun pushPhotoData(photoData: PhotoData){
         viewModelScope.launch {
             try {
                 val response = repository.postPhoto(photoData)
                 myResponse.value = response

                 Log.d("photoposted", response.body()!!.name)
             }catch (e: Exception){
                 Log.d("exception", e.message!!)
             }

         }
    }

    init {
        fetchUserDetails()
    }

    fun fetchUserDetails(){
         viewModelScope.launch {
             try {
                val response = repository.getUserDetails()
                userDetailsLiveData.value = response

             }catch (e: Exception){
                Log.d("userException", e.message.toString())
             }
         }
    }

}