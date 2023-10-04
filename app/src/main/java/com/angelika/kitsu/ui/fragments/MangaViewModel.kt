package com.angelika.kitsu.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angelika.kitsu.data.repositories.MangaRepository
import com.angelika.kitsu.models.MangaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaRepository: MangaRepository
) : ViewModel() {

    private val _mangaLiveData = MutableLiveData(MangaUiState<List<MangaModel>>())
    val mangaLiveData: LiveData<MangaUiState<List<MangaModel>>> = _mangaLiveData

    init {
        getData()
    }

    private fun getData() {
        mangaRepository.getData(
            onResponse = { data ->
                val newValue =
                    _mangaLiveData.value?.copy(isLoading = false, success = data.data)
                _mangaLiveData.value = newValue
            },
            onFailure = { message ->
                val newValue = _mangaLiveData.value?.copy(isLoading = false, error = message)
                _mangaLiveData.value = newValue
            }
        )
    }
}

data class MangaUiState<T>(
    val isLoading: Boolean = true,
    val error: String? = null,
    val success: T? = null
)