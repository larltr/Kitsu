package com.angelika.kitsu.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.angelika.kitsu.data.repositories.MangaRepository
import com.angelika.kitsu.models.MangaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaRepository: MangaRepository
) : ViewModel() {

    fun getData() = mangaRepository.getManga().cachedIn(viewModelScope)
}