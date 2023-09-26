package com.blissvine.swach.activities


import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri

import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.blissvine.swach.R

import com.blissvine.swach.databinding.ActivityScanBinding

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

import com.blissvine.swach.database.Authentication
import com.google.android.gms.location.LocationRequest
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class ScanActivity : AppCompatActivity() {


    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var address: String? = null

    private val REQUEST_CODE = 100

    private lateinit var binding : ActivityScanBinding



    lateinit var file : File
    lateinit var outputStream: FileOutputStream

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);




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

        val photoName = binding.photoname
        binding.submitBtn.setOnClickListener {
            if(img!=null){
                uploadImage(file, photoName.text.toString(), address!!)
            }

            startActivity(Intent(this@ScanActivity, MainActivity::class.java))
            finishAffinity()


        }

        binding.selectCurrentLocation.setOnClickListener {
            getLastLocation()
        }




        if(!isLocationEnabled()){
            Toast.makeText(this, "Your location provider is turned off. Please turn it on.", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)

        }else{

            Dexter.withActivity(this).withPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if(report!!.areAllPermissionsGranted()){
                       getLastLocation()
                    }

//                    if(report.isAnyPermissionPermanentlyDenied){
//                        Toast.makeText(this@ScanActivity, "You have denied location permission. Please enable them as it is mandatory for the app to work.", Toast.LENGTH_SHORT).show()
//                    }

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationDialogForPermissions()
                }

            }).onSameThread().check()

        }






    }


    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient!!.getLastLocation()
                .addOnSuccessListener { location ->
                    if (location != null) {
                        try {
                            val geocoder = Geocoder(this@ScanActivity, Locale.getDefault())
                            val addresses: List<Address>? =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            //  lattitude.setText("Lattitude: " + addresses!![0].getLatitude())
                            //  longitude.setText("Longitude: " + addresses[0].getLongitude())
//                            binding.dummy.setText(
//                                "Address: " + addresses?.get(0)!!.getAddressLine(0)
//                            )

                                   address = "Address: " + addresses?.get(0)!!.getAddressLine(0)
                                   binding.photoaddresss.setText( "Address: " + addresses?.get(0)!!.getAddressLine(0))
                            //     address.setText("Address: " + addresses[0].getAddressLine(0))
//                            city.setText("City: " + addresses[0].getLocality())
//                            country.setText("Country: " + addresses[0].getCountryName())
                        } catch (e: IOException) {
                            Log.d("location", e.message.toString())
                        }
                    }
                }
        }
    }

//    private fun askPermission() {
//        ActivityCompat.requestPermissions(
//            this@ScanActivity,
//            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
//            REQUEST_CODE
//        )
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation()
//            } else {
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//                Toast.makeText(
//                    this@ScanActivity,
//                    "Please provide the required permission",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }


    private fun isLocationEnabled(): Boolean{
        // This provides access to the system location services.
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }


    private fun showRationDialogForPermissions(){
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ){ _, _, ->

                try{
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    // we needed to give uri link of our app so that it opens the settings for our particular app
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }

            }.setNegativeButton("Cancel"){ dialog, _ ->
                dialog.dismiss()

            }.show()
    }

    fun uploadImage(file: File , name : String, location : String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://swachh-w8p5.onrender.com")
            .build()


        val service = retrofit.create(Authentication::class.java)

        val name1: RequestBody = RequestBody.create(MediaType.parse("text/plain"), name)
        val location1: RequestBody = RequestBody.create(MediaType.parse("text/plain"), location)

        val requestBody =   RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("photo",file.name,requestBody)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res=service.uploadAttachment(name1,location1,part)
                Log.d("imageres",res.toString())
            }catch (e: Exception){
                Toast.makeText(this@ScanActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }
}