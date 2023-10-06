package com.angelika.kitsu.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.angelika.kitsu.data.remote.apiservice.MangaApiService
import com.angelika.kitsu.data.remote.pagingsources.MangaPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaRepository @Inject constructor(
    private val mangaApiService: MangaApiService
) {

    fun getManga() = Pager(
        PagingConfig(
            pageSize = 10,
            initialLoadSize = 20
        )
    ) {
        MangaPagingSource(mangaApiService)
    }.liveData
}