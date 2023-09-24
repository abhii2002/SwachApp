package com.blissvine.swach.models

import android.media.Image
import com.squareup.moshi.Json
import retrofit2.http.POST


data  class PhotoData(

      val name: String,
      val location: String,
      val image: Int
)


