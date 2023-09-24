package com.blissvine.swach.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.blissvine.swach.R
import com.blissvine.swach.databinding.ActivityMainBinding
import com.blissvine.swach.databinding.ActivityScanBinding
import com.blissvine.swach.models.PhotoData
import com.blissvine.swach.repository.Repository
import com.blissvine.swach.viewmodel.MainViewModel
import com.blissvine.swach.viewmodel.MainViewModelFactory

class ScanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanBinding
    //private lateinit var viewModel: MainViewModel
    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this@ScanActivity).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScanBinding.inflate(layoutInflater)
        val view  = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

//        val repository = Repository()
//        val viewModelFactory = MainViewModelFactory(repository = )
//        viewModel = ViewModelProvider(this, viewmodfa)



        val img=intent.getStringExtra("image")
        val imageView : ImageView =findViewById(R.id.imageViewscan)

        if(img!=null){
            Log.d("intent",img)
            imageView.setImageURI(img.toUri())
        }
        val btn : Button = findViewById(R.id.submitBtn)
        btn.setOnClickListener {
          //  Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()



        }




    }




}