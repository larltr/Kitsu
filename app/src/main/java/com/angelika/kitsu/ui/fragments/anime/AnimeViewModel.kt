package com.angelika.kitsu.ui.fragments.anime

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.angelika.kitsu.data.repositories.KitsuRepository
import com.angelika.kitsu.models.KitsuModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: KitsuRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _animeState = MutableStateFlow<PagingData<KitsuModel>>(PagingData.empty())
    val animeState = _animeState.asStateFlow()

    private val seasonQueryState: StateFlow<String> =
        savedStateHandle.getStateFlow(SEASON_QUERY_KEY, "winter")

    init {
        getAnime()
    }

    fun setSeasonQuery(season: String) {
        savedStateHandle[SEASON_QUERY_KEY] = season
    }

    private fun getAnime() {
        viewModelScope.launch {
            seasonQueryState.flatMapLatest { season ->
                animeRepository.getAnime(season)
            }.collectLatest { animeData ->
                _animeState.value = animeData
            }
        }
    }

    companion object {
        const val SEASON_QUERY_KEY = "season_query_key"
        const val CHAPTER_COUNT_QUERY_KEY = "chapter_count_query_key"
    }
}