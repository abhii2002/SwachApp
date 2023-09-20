package com.blissvine.swach

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.blissvine.swach.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    lateinit var imageUri : Uri

    val contracts =registerForActivityResult(ActivityResultContracts.TakePicture()){
        Log.d("image",imageUri.toString())
        if(imageUri!=null){
            val intent= Intent(applicationContext,ScanActivity::class.java)
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

    }

    fun createImageUri() : Uri? {
        val image= File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,"com.blissvine.swach.fileProvider",image)
    }
}