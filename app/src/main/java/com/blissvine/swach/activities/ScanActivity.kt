package com.blissvine.swach.activities

<<<<<<< HEAD
import android.Manifest
import android.R.attr.country
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
=======
import android.content.Intent
>>>>>>> 747f1ba180c21bf6baeb5ba31cb3f9dfc4a094f9
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.blissvine.swach.R
<<<<<<< HEAD
import com.blissvine.swach.databinding.ActivityScanBinding
import com.blissvine.swach.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import java.io.IOException
import java.util.Locale
=======
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
>>>>>>> 747f1ba180c21bf6baeb5ba31cb3f9dfc4a094f9


class ScanActivity : AppCompatActivity() {

<<<<<<< HEAD
    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private val REQUEST_CODE = 100

    private lateinit var binding : ActivityScanBinding
    //private lateinit var viewModel: MainViewModel
    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this@ScanActivity).get(MainViewModel::class.java)
    }
=======
    private lateinit var binding : ActivityMainBinding
    lateinit var file : File
    lateinit var outputStream: FileOutputStream
>>>>>>> 747f1ba180c21bf6baeb5ba31cb3f9dfc4a094f9
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        setContentView(view)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        val repository = Repository()
//        val viewModelFactory = MainViewModelFactory(repository = )
//        viewModel = ViewModelProvider(this, viewmodfa)



=======
        setContentView(R.layout.activity_scan)
>>>>>>> 747f1ba180c21bf6baeb5ba31cb3f9dfc4a094f9
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
<<<<<<< HEAD
          //  Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()

             getLastLocation()

=======
            Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()
            if(img!=null){
                uploadImage(file,n1.text.toString(),n2.text.toString())
            }
>>>>>>> 747f1ba180c21bf6baeb5ba31cb3f9dfc4a094f9
        }
    }

<<<<<<< HEAD
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
=======
    fun uploadImage(file: File , name : String,location : String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://swachh-w8p5.onrender.com")
            .build()
>>>>>>> 747f1ba180c21bf6baeb5ba31cb3f9dfc4a094f9

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