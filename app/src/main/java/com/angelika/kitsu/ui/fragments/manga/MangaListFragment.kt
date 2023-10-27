package com.angelika.kitsu.ui.fragments.manga

import android.os.Bundle
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
import com.angelika.kitsu.databinding.FragmentMangaListBinding
import com.angelika.kitsu.ui.adapters.KitsuAdapter
import com.angelika.kitsu.ui.adapters.ListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MangaListFragment : Fragment(R.layout.fragment_manga_list) {

    private val binding by viewBinding(FragmentMangaListBinding::bind)
    private val mangaViewModel by viewModels<MangaViewModel>()
    private val mangaAdapter = KitsuAdapter()
    private val mangaListLoadStateAdapter = ListLoadStateAdapter()
    private val chapterCountList = arrayOf("1", "2", "3", "4", "5", "6", "7")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToManga()
        setupSubscribe()
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
        svChapterCount.setOnQueryTextListener((object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                for (i in chapterCountList) {
                    if (i == query) {
                        searchChapterCount(query)
                        Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                for (i in chapterCountList) {
                    if (i == newText) {
                        searchChapterCount(newText)
                    }
                }
                return false
            }
        }))
    }

    private fun searchChapterCount(query: String) {
        mangaViewModel.setChapterCountQuery(query)
    }

    private fun setupSubscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            mangaViewModel.mangaState.collectLatest { data ->
                mangaAdapter.submitData(data)
            }
        }
    }

    private fun setupListeners() = with(binding) {
        mangaAdapter.addLoadStateListener {
            when (it.refresh) {
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