package com.angelika.kitsu.ui.fragments.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.angelika.kitsu.data.repositories.KitsuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaRepository: KitsuRepository
) : ViewModel() {

    fun getManga() = mangaRepository.getManga().cachedIn(viewModelScope)
}