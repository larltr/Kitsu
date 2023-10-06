package com.angelika.kitsu.data.remote.apiservice

import com.angelika.kitsu.models.MangaModel
import com.angelika.kitsu.models.MangaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApiService {

    @GET("manga")
    suspend fun fetchManga(
        @Query("page[limit]") pageSize: Int,
        @Query("page[offset]") offset: Int
    ): MangaResponse<MangaModel>
}