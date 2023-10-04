package com.angelika.kitsu.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.angelika.kitsu.R
import com.angelika.kitsu.databinding.FragmentAnimeListBinding
import com.angelika.kitsu.ui.adapters.MangaAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MangaListFragment : Fragment(R.layout.fragment_anime_list) {

    private val binding by viewBinding(FragmentAnimeListBinding::bind)
    private val viewModel by viewModels<MangaViewModel>()
    private val mangaAdapter = MangaAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToManga()
    }

    private fun initialize() {
        binding.recyclerView.apply {
            adapter = mangaAdapter
        }
    }

    private fun subscribeToManga() {
        viewModel.mangaLiveData.observe(viewLifecycleOwner) {
            it.error?.let {
                Log.e("error", it.toString())
                Toast.makeText(requireContext(), "You have big problems bro", Toast.LENGTH_SHORT)
                    .show()
            }
            it.success?.let { manga ->
                mangaAdapter.submitList(manga)
            }
        }
    }
}