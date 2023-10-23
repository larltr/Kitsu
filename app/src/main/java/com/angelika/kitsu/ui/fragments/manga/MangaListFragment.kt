package com.angelika.kitsu.ui.fragments.manga

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.angelika.kitsu.R
import com.angelika.kitsu.databinding.FragmentMangaListBinding
import com.angelika.kitsu.ui.adapters.KitsuAdapter
import com.angelika.kitsu.ui.adapters.ListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MangaListFragment : Fragment(R.layout.fragment_manga_list) {

    private val binding by viewBinding(FragmentMangaListBinding::bind)
    private val mangaViewModel by viewModels<MangaViewModel>()
    private val mangaAdapter = KitsuAdapter()
    private val mangaListLoadStateAdapter = ListLoadStateAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToManga()
        setupListeners()
    }

    private fun initialize() {
        val gridLayoutManager = GridLayoutManager(activity, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = mangaAdapter.getItemViewType(position)
                return if (viewType == mangaAdapter.WALLPAPER_VIEW_TYPE) 1
                else 3
            }
        }
        binding.rvManga.apply {
            this.layoutManager = gridLayoutManager
            this.setHasFixedSize(true)
        }
        val footerAdapter = mangaAdapter.withLoadStateFooter(mangaListLoadStateAdapter)
        binding.rvManga.adapter = footerAdapter
    }


    private fun subscribeToManga() = with(binding) {
        mangaViewModel.getManga()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mangaViewModel.mangaState.collect {
                    mangaAdapter.submitData(it)
                }
            }
        }
    }

    private fun setupListeners() = with(binding) {
        mangaAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.Error -> {
                }
                LoadState.Loading -> {
                    progressBarManga.isInvisible = true
                }
                is LoadState.NotLoading -> {
                    progressBarManga.isInvisible = true
                }
            }
        }
    }
}