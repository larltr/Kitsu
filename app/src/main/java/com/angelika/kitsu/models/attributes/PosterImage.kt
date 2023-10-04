package com.angelika.kitsu.models.attributes

import com.google.gson.annotations.SerializedName

data class PosterImage(
    @SerializedName("original")
    val original: String
)