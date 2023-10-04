package com.angelika.kitsu.models

import com.angelika.kitsu.models.attributes.Attributes
import com.google.gson.annotations.SerializedName

data class MangaModel(

    @SerializedName("id")
    val id: String,

    @SerializedName("attributes")
    val attributes: Attributes
)