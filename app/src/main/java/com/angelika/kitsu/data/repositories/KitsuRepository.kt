package com.angelika.kitsu.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.angelika.kitsu.data.remote.apiservice.KitsuApiService
import com.angelika.kitsu.data.remote.pagingsources.AnimePagingSource
import com.angelika.kitsu.data.remote.pagingsources.MangaPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KitsuRepository @Inject constructor(
    private val kitsuApiService: KitsuApiService
) {

    fun getManga() = Pager(
        PagingConfig(
            pageSize = 15,
            initialLoadSize = 15
        )
    ) {
        MangaPagingSource(kitsuApiService)
    }.liveData

    fun getAnime() = Pager(
        PagingConfig(
            pageSize = 15,
            initialLoadSize = 15
        )
    ) {
        AnimePagingSource(kitsuApiService)
    }.liveData
}