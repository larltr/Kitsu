package com.angelika.kitsu.data.remote.apiservice

import com.angelika.kitsu.models.MangaModel
import com.angelika.kitsu.models.MangaResponse
import retrofit2.Call
import retrofit2.http.GET

interface MangaApiService {

    @GET("manga")
    fun fetchManga(): Call<MangaResponse<MangaModel>>
}