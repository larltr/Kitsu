package com.angelika.kitsu.data.remote.pagingsources

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.angelika.kitsu.data.remote.apiservice.KitsuApiService
import com.angelika.kitsu.models.KitsuModel
import retrofit2.HttpException
import java.io.IOException

class AnimePagingSource(
    private val animeApiService: KitsuApiService
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
            val response = animeApiService.fetchAnime(pageSize, position)
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