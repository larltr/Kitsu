package com.angelika.kitsu.ui.fragments.anime

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.angelika.kitsu.R
import com.angelika.kitsu.databinding.FragmentAnimeListBinding
import com.angelika.kitsu.ui.adapters.KitsuAdapter
import com.angelika.kitsu.ui.adapters.ListLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeListFragment : Fragment(R.layout.fragment_anime_list) {

    private val binding by viewBinding(FragmentAnimeListBinding::bind)
    private val animeAdapter = KitsuAdapter()
    private val animeListLoadStateAdapter = ListLoadStateAdapter()
    private val animeViewModel by viewModels<AnimeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToAnime()
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
            this.adapter = animeAdapter
        }
        binding.rvAnime.adapter = animeAdapter
        val footerAdapter = animeAdapter.withLoadStateFooter(animeListLoadStateAdapter)
        binding.rvAnime.adapter = footerAdapter
    }

    private fun subscribeToAnime() {
        animeViewModel.getAnime().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                animeAdapter.submitData(it)
            }

            lifecycleScope.launch {
                delay(1500)
                binding.progressBarAnime.isInvisible = true
            }
        }
    }
}