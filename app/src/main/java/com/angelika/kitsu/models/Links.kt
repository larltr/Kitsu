package com.angelika.kitsu.models

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("next")
    val next: String,

    @SerializedName("last")
    val last: String,

    @SerializedName("prev")
    val prev: String,

    @SerializedName("first")
    val first: String
)