package com.blissvine.swach.activities

import android.R.attr.password
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blissvine.swach.R
import com.blissvine.swach.adaters.BannersMainAdapter
import com.blissvine.swach.adaters.GuidelinesAdapter
import com.blissvine.swach.database.Authentication
import com.blissvine.swach.databinding.ActivityMainBinding
import com.blissvine.swach.models.GuideLinesModel
import com.blissvine.swach.models.PhotoData
import com.blissvine.swach.viewmodel.MainViewModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.File


class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding

    lateinit var imageUri : Uri

    private val adapter : BannersMainAdapter by lazy { BannersMainAdapter() }

    private val guidelinesAdapter : GuidelinesAdapter by lazy { GuidelinesAdapter() }

    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    }

    val contracts = registerForActivityResult(ActivityResultContracts.TakePicture()){
        Log.d("image",imageUri.toString())
        if(imageUri!=null){
            val intent= Intent(applicationContext, ScanActivity::class.java)
            intent.putExtra("image",imageUri.toString())
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        imageUri = createImageUri()!!
        binding.scanBtn.setOnClickListener {
            contracts.launch(imageUri)
        }

//        val bannerModel = listOf(BannerModel(R.drawable.banner_image_1),
//            BannerModel(R.drawable.banner_image_5),
//            BannerModel(R.drawable.banner_image_3),
//            BannerModel(R.drawable.banner_image_4),
//            BannerModel(R.drawable.banner_image_2))
//
//        adapter.setData(bannerModel)
//        binding.rvBannersFront.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvBannersFront.adapter = adapter

        // guidlines adapter setup

        val dummyGuidlinesData = listOf<GuideLinesModel>(
            GuideLinesModel(
                "Dummy text",
                "Hello hi, attitude bye, lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "Hello hi, attitude bye, lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "Hello hi, attitude bye, lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "Hello hi, attitude bye, lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "Hello hi, attitude bye, lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),

            )

        guidelinesAdapter.setData(dummyGuidlinesData)
        binding.rvGuidlinesNotice.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvGuidlinesNotice.adapter = guidelinesAdapter


        viewModel.userDetailsLiveData.observe(this) { response ->
            if (response.isSuccessful) {
                Log.d("usersare", response.body().toString())
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }

        }





//        val myPost = PhotoData("It is working.")

        viewModel.pushPhotoData(PhotoData("s", "s", R.drawable.main_image))

        viewModel.myResponse.observe(this){
               if (it.isSuccessful){
                    Log.d("s", it.body().toString())
               }
        }



        }


    fun createImageUri() : Uri? {
        val image= File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,"com.blissvine.swach.fileProvider",image)
    }



}
