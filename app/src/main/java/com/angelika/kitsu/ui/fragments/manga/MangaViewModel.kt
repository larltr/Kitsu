package com.angelika.kitsu.ui.fragments.manga

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
class MangaViewModel @Inject constructor(
    private val mangaRepository: KitsuRepository
) : ViewModel() {

    private val _mangaState = MutableStateFlow<PagingData<KitsuModel>>(PagingData.empty())
    val mangaState = _mangaState.asStateFlow()

    fun getManga() {
        viewModelScope.launch {
            mangaRepository.getManga().cachedIn(viewModelScope).collect {
                _mangaState.value = it
            }
        }
    }
}