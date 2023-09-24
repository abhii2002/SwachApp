package com.blissvine.swach.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.blissvine.swach.R
import com.blissvine.swach.database.Authentication
import com.blissvine.swach.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream


class ScanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var file : File
    lateinit var outputStream: FileOutputStream
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val img=intent.getStringExtra("image")
        val imageView : ImageView =findViewById(R.id.imageViewscan)

        if(img!=null){
            Log.d("intent",img)
            imageView.setImageURI(img.toUri())
            val filesDir=applicationContext.filesDir
            file=File(filesDir,"image.png")
            val inputStream = contentResolver.openInputStream(img.toUri())
            outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)

        }
        val btn : Button = findViewById(R.id.submitBtn)

        val n1 :EditText = findViewById(R.id.editTextTextscan)
        val n2 :EditText = findViewById(R.id.editTextTextPostalAddressscan)
        btn.setOnClickListener {
            Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()
            if(img!=null){
                uploadImage(file,n1.text.toString(),n2.text.toString())
            }
        }
    }

    fun uploadImage(file: File , name : String,location : String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://swachh-w8p5.onrender.com")
            .build()

        val service = retrofit.create(Authentication::class.java)

        //val name="manvi"
        //val location="noida"
        val name1: RequestBody = RequestBody.create(MediaType.parse("text/plain"), name)
        val location1: RequestBody = RequestBody.create(MediaType.parse("text/plain"), location)

        val requestBody =   RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("photo",file.name,requestBody)

        CoroutineScope(Dispatchers.IO).launch {
            val res=service.uploadAttachment(name1,location1,part)
            Log.d("imageres",res.toString())
        }
    }
}