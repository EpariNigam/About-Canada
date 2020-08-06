package com.example.aboutcanada.model

import com.google.gson.annotations.SerializedName

data class FactItem(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageHref")
    val imageHref: String?
)