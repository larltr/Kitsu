package com.angelika.kitsu.models.attributes

import com.google.gson.annotations.SerializedName

data class PosterImage(
    @SerializedName("small")
    val small: String
)