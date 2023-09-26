package com.blissvine.swach.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blissvine.swach.R
import com.blissvine.swach.adaters.VendorsAdapter
import com.blissvine.swach.databinding.ActivityRecycledWasteBinding
import com.blissvine.swach.models.VendorsModel

class RecycledWasteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecycledWasteBinding

    private val vendorsAdapter : VendorsAdapter by lazy { VendorsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRecycledWasteBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)


        val dummyVendorsData = listOf<VendorsModel>(VendorsModel("Priyanshu", R.drawable.simpson),
            VendorsModel("Vashu Mittal", R.drawable.simpson),
            VendorsModel("Jaya Mishra", R.drawable.simpson),
            VendorsModel("Kim jon un", R.drawable.main_image),
            VendorsModel("Priyanshu Gupta", R.drawable.main_image))


        vendorsAdapter.setData(dummyVendorsData)
        binding.rvVendorsList.layoutManager =
            LinearLayoutManager(this@RecycledWasteActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvVendorsList.adapter = vendorsAdapter


    }
}