package com.blissvine.swach.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blissvine.swach.R
import com.blissvine.swach.adaters.BannersMainAdapter
import com.blissvine.swach.adaters.GuidelinesAdapter
import com.blissvine.swach.databinding.ActivityMainBinding
import com.blissvine.swach.models.BannerModel
import com.blissvine.swach.models.GuideLinesModel
import com.blissvine.swach.viewmodel.SharedViewModel
import java.io.File

class MainActivity : BaseActivity() {

    private lateinit var binding : ActivityMainBinding

    lateinit var imageUri : Uri

    private val guidelinesAdapter : GuidelinesAdapter by lazy { GuidelinesAdapter() }

    private val viewModel : SharedViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(SharedViewModel::class.java)
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
        imageUri=createImageUri()!!
        binding.scanBtn.setOnClickListener {
            contracts.launch(imageUri)
        }

        // guidlines adapter setup

        val dummyGuidlinesData = listOf<GuideLinesModel>(
            GuideLinesModel(
                "Dummy text",
                "daskdnidni lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "dasjdbsaudbauod lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "dsadasdasd lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "dasjdiabuab lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),
            GuideLinesModel(
                "Dummy text",
                "dsadihdi lorem epsum jdnasido ehwiueh30 hfiewjfie dwdiaidna uienfd"
            ),

            )


        viewModel.guidelinesDetailsLiveData.observe(this@MainActivity){
            if (it.isSuccessful){
                guidelinesAdapter.setData(it.body()!!)
                binding.rvGuidlinesNotice.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.rvGuidlinesNotice.adapter = guidelinesAdapter
            }
        }


        binding.dailyWaste.setOnClickListener {
            startActivity(Intent(this,DailyWasteActivity::class.java))
        }

        binding.recycledWaste.setOnClickListener {
            startActivity(Intent(this, RecycledWasteActivity::class.java))
        }






    }

    fun createImageUri() : Uri? {
        val image= File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,"com.blissvine.swach.fileProvider",image)
    }


}
