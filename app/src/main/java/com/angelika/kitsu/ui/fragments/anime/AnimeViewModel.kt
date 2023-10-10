package com.angelika.kitsu.ui.fragments.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.angelika.kitsu.data.repositories.KitsuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: KitsuRepository
) : ViewModel() {

    fun getAnime() = animeRepository.getAnime().cachedIn(viewModelScope)
}