package com.blissvine.swach.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blissvine.swach.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
         binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

    }
}