package com.angelika.kitsu.data.repositories

import com.angelika.kitsu.data.remote.apiservice.MangaApiService
import com.angelika.kitsu.models.MangaModel
import com.angelika.kitsu.models.MangaResponse
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(
    private val mangaApiService: MangaApiService
) {

    fun getData(
        onResponse: (data: MangaResponse<MangaModel>) -> Unit,
        onFailure: (errorMassage: String) -> Unit
    ) {
        mangaApiService.fetchManga()
            .enqueue(object : retrofit2.Callback<MangaResponse<MangaModel>> {
                override fun onResponse(
                    call: Call<MangaResponse<MangaModel>>,
                    response: Response<MangaResponse<MangaModel>>
                ) {
                    if (response.isSuccessful)
                        response.body()?.let(onResponse)
                }

                override fun onFailure(
                    call: Call<MangaResponse<MangaModel>>,
                    t: Throwable
                ) {
                    onFailure(t.localizedMessage ?: "Error")
                }
            })
    }
}