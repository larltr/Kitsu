package com.angelika.kitsu.ui.fragments.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.angelika.kitsu.data.repositories.KitsuRepository
import com.angelika.kitsu.models.KitsuModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: KitsuRepository
) : ViewModel() {

    private val _animeState = MutableStateFlow<PagingData<KitsuModel>>(PagingData.empty())
    val animeState = _animeState.asStateFlow()

    fun getAnime() {
        viewModelScope.launch {
            animeRepository.getAnime().cachedIn(viewModelScope).collect {
                _animeState.value = it
            }
        }
    }
}