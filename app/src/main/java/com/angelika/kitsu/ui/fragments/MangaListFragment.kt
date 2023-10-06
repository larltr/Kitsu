package com.angelika.kitsu.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.angelika.kitsu.R
import com.angelika.kitsu.databinding.FragmentMangaListBinding
import com.angelika.kitsu.models.MangaModel
import com.angelika.kitsu.ui.adapters.MangaAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MangaListFragment : Fragment(R.layout.fragment_manga_list) {

    private val binding by viewBinding(FragmentMangaListBinding::bind)
    private val viewModel by viewModels<MangaViewModel>()
    private val mangaAdapter = MangaAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToManga()
    }

    private fun initialize() {
        binding.recyclerView.adapter = mangaAdapter
    }

    private fun subscribeToManga() = with(binding) {
            viewModel.getData().observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    mangaAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            delay(1500)
            progressBarFragment.isInvisible = true
        }
    }
}