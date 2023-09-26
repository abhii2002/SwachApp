package com.blissvine.swach.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blissvine.swach.R
import com.blissvine.swach.adaters.VendorsAdapter
import com.blissvine.swach.databinding.ActivityRecycledWasteBinding
import com.blissvine.swach.models.VendorsModel
import com.blissvine.swach.utils.Constants
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

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

        vendorsAdapter.setOnClickListener(object : VendorsAdapter.OnClickListener{
            override fun onClick(position: Int, model: VendorsModel) {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<VendorsModel> = moshi.adapter(VendorsModel::class.java)
                var jSonModel = jsonAdapter.toJson(model)
                val intent = Intent(this@RecycledWasteActivity, VendorDetailsActivity::class.java)
                intent.putExtra(Constants.VENDORS_EXTRA_DETAILS, jSonModel)
                startActivity(intent)
            }

        })


    }
}