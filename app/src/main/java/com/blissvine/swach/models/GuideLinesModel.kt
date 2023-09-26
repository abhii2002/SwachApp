package com.blissvine.swach.models

import com.squareup.moshi.Json


data class GuideLinesModel(
    @Json(name = "headline")
    val headline: String,
    @Json(name = "guidelines")
    val guidelines: String,
    @Json(name = "date")
    val date:  String
)