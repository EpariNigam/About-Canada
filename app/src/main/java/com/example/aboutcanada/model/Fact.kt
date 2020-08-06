package com.example.aboutcanada.model

import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("title")
    val title: String,
    @SerializedName("rows")
    val rows: List<FactItem>
)