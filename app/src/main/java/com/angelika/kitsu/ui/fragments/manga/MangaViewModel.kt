package com.angelika.kitsu.ui.fragments.manga

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.angelika.kitsu.data.repositories.KitsuRepository
import com.angelika.kitsu.models.KitsuModel
import com.angelika.kitsu.ui.fragments.anime.AnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaRepository: KitsuRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _mangaState = MutableStateFlow<PagingData<KitsuModel>>(PagingData.empty())
    val mangaState = _mangaState.asStateFlow()

    private val chapterCountQueryState: StateFlow<String> =
        savedStateHandle.getStateFlow(AnimeViewModel.CHAPTER_COUNT_QUERY_KEY, "1")

    init {
        getManga()
    }

    fun setChapterCountQuery(chapterCount: String) {
        savedStateHandle[AnimeViewModel.CHAPTER_COUNT_QUERY_KEY] = chapterCount
    }

    private fun getManga() {
        viewModelScope.launch {
            chapterCountQueryState.flatMapLatest { chapterCount ->
                mangaRepository.getManga(chapterCount)
            }.collectLatest { mangaData ->
                _mangaState.value = mangaData
            }
        }
    }
}