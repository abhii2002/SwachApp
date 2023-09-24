package com.blissvine.swach.activities

import android.Manifest
import android.R.attr.country
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.blissvine.swach.R
import com.blissvine.swach.databinding.ActivityScanBinding
import com.blissvine.swach.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import java.io.IOException
import java.util.Locale


class ScanActivity : AppCompatActivity() {

    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private val REQUEST_CODE = 100

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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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

             getLastLocation()

        }




    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient!!.getLastLocation()
                .addOnSuccessListener { location ->
                    if (location != null) {
                        try {
                            Toast.makeText(this@ScanActivity, "Worked", Toast.LENGTH_SHORT).show()
                            val geocoder = Geocoder(this@ScanActivity, Locale.getDefault())
                            val addresses: List<Address>? =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            //  lattitude.setText("Lattitude: " + addresses!![0].getLatitude())
                            //  longitude.setText("Longitude: " + addresses[0].getLongitude())
//                            binding.dummy.setText(
//                                "Address: " + addresses?.get(0)!!.getAddressLine(0)
//                            )
                               binding.editTextTextPostalAddressscan.setText("Address: " + addresses?.get(0)!!.getAddressLine(0))
                            //     address.setText("Address: " + addresses[0].getAddressLine(0))
//                            city.setText("City: " + addresses[0].getLocality())
//                            country.setText("Country: " + addresses[0].getCountryName())
                        } catch (e: IOException) {
                            Log.d("location", e.message.toString())
                        }
                    }
                }
        } else {
            askPermission()
        }
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this@ScanActivity,
            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(
                    this@ScanActivity,
                    "Please provide the required permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}