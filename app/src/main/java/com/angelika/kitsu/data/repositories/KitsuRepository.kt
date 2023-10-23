package com.angelika.kitsu.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.angelika.kitsu.data.remote.apiservice.KitsuApiService
import com.angelika.kitsu.data.remote.pagingsources.AnimePagingSource
import com.angelika.kitsu.data.remote.pagingsources.MangaPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
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
    }.flow.flowOn(Dispatchers.IO)

    fun getAnime() = Pager(
        PagingConfig(
            pageSize = 15,
            initialLoadSize = 15
        )
    ) {
        AnimePagingSource(kitsuApiService)
    }.flow.flowOn(Dispatchers.IO)
}