package com.angelika.kitsu.ui.fragments.anime

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.angelika.kitsu.R
import com.angelika.kitsu.databinding.FragmentAnimeListBinding
import com.angelika.kitsu.ui.adapters.KitsuAdapter
import com.angelika.kitsu.ui.adapters.ListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListFragment : Fragment(R.layout.fragment_anime_list) {

    private val binding by viewBinding(FragmentAnimeListBinding::bind)
    private val animeViewModel by viewModels<AnimeViewModel>()
    private val animeAdapter = KitsuAdapter()
    private val animeListLoadStateAdapter = ListLoadStateAdapter()
    private val seasonList = arrayOf("winter", "summer", "fall", "spring")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToAnime()
        setupSubscribe()
        setupListeners()
    }

    private fun initialize() {
        val gridLayoutManager = GridLayoutManager(activity, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = animeAdapter.getItemViewType(position)
                return if (viewType == animeAdapter.WALLPAPER_VIEW_TYPE) 1
                else 3
            }
        }
        binding.rvAnime.apply {
            this.layoutManager = gridLayoutManager
            this.setHasFixedSize(true)
        }
        val footerAdapter = animeAdapter.withLoadStateFooter(animeListLoadStateAdapter)
        binding.rvAnime.adapter = footerAdapter
    }

    private fun subscribeToAnime() = with(binding) {
        svSeason.setOnQueryTextListener((object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                for (i in seasonList) {
                    if (i == query) {
                        searchSeason(query)
                        Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                for (i in seasonList) {
                    if (i == newText) {
                        searchSeason(newText)
                    }
                }
                return false
            }
        }))
    }

    private fun searchSeason(query: String) {
        animeViewModel.setSeasonQuery(query)
    }

    private fun setupSubscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            animeViewModel.animeState.collectLatest { data ->
                animeAdapter.submitData(data)
            }
        }
    }

    private fun setupListeners() = with(binding) {
        animeAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    Log.e("error", (it.refresh as LoadState.Error).error.message.toString())
                }

                LoadState.Loading -> {
                    progressBarAnime.isInvisible = true
                }

                is LoadState.NotLoading -> {
                    progressBarAnime.isInvisible = true
                }
            }
        }
    }
}