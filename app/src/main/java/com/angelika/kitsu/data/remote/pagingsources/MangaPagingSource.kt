package com.angelika.kitsu.data.remote.pagingsources

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.angelika.kitsu.data.remote.apiservice.KitsuApiService
import com.angelika.kitsu.models.KitsuModel
import okio.IOException
import retrofit2.HttpException

class MangaPagingSource(
    private val mangaApiService: KitsuApiService,
    private val chapterCount: String = "1"
) : PagingSource<Int, KitsuModel>() {

    override fun getRefreshKey(state: PagingState<Int, KitsuModel>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KitsuModel> {
        val pageSize = params.loadSize
        val position = params.key ?: 1
        return try {
            val response = mangaApiService.fetchManga(pageSize = pageSize, offset = position, chapterCount = chapterCount)
            val nextPageNumber =
                Uri.parse(response.links.next).getQueryParameter("page[offset]")!!.toInt()
            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}