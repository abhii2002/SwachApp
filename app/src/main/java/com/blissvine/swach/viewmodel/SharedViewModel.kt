package com.blissvine.swach.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blissvine.swach.models.GuideLinesModel
import com.blissvine.swach.network.ApiClient
import com.blissvine.swach.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class SharedViewModel(private val repository: Repository = Repository(ApiClient.apiService)) : ViewModel(){

    var guidelinesDetailsLiveData : MutableLiveData<Response<List<GuideLinesModel>>> = MutableLiveData()

    init {
        fetchGuidelines()
    }



    fun fetchGuidelines(){
        viewModelScope.launch {
            try {
                val response = repository.fetchGuidlines()
                guidelinesDetailsLiveData.value = response

            }catch (e: Exception){
                Log.d("userException", e.message.toString())
            }
        }
    }



}