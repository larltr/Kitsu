package com.angelika.kitsu.data.remote.pagingsources

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.angelika.kitsu.data.remote.apiservice.MangaApiService
import com.angelika.kitsu.models.MangaModel
import okio.IOException
import retrofit2.HttpException

class MangaPagingSource(
    private val mangaApiService: MangaApiService
) : PagingSource<Int, MangaModel>() {

    override fun getRefreshKey(state: PagingState<Int, MangaModel>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaModel> {
        val pageSize = params.loadSize
        val position = params.key ?: 1
        return try {
            val response = mangaApiService.fetchManga(pageSize, position)
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