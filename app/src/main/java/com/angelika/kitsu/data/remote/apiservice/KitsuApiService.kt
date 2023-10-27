package com.angelika.kitsu.data.remote.apiservice

import com.angelika.kitsu.models.KitsuModel
import com.angelika.kitsu.models.KitsuResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KitsuApiService {

    @GET("manga")
    suspend fun fetchManga(
        @Query("page[limit]") pageSize: Int,
        @Query("page[offset]") offset: Int,
        @Query("filter[chapterCount]") chapterCount: String = "1"
    ): KitsuResponse<KitsuModel>

    @GET("anime")
    suspend fun fetchAnime(
        @Query("page[limit]") pageSize: Int,
        @Query("page[offset]") offset: Int,
        @Query("filter[season]") season: String = "winter"
    ): KitsuResponse<KitsuModel>
}