package com.blissvine.swach.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.blissvine.swach.R
import com.blissvine.swach.databinding.ActivityMainBinding

class ScanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val img=intent.getStringExtra("image")
        val imageView : ImageView =findViewById(R.id.imageViewscan)

        if(img!=null){
            Log.d("intent",img)
            imageView.setImageURI(img.toUri())
        }
        val btn : Button = findViewById(R.id.submitBtn)
        btn.setOnClickListener {
            Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()
        }
    }
}