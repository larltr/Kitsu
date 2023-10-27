package com.angelika.kitsu.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.angelika.kitsu.ui.fragments.anime.AnimeListFragment
import com.angelika.kitsu.ui.fragments.manga.MangaListFragment
import java.lang.IllegalArgumentException

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_TABS = 2
    }

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AnimeListFragment()
            1 -> MangaListFragment()
            else -> throw IllegalArgumentException("p: $position")
        }
    }
}