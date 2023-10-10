package com.angelika.kitsu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angelika.kitsu.databinding.ItemFooterBinding

class ListLoadStateAdapter : LoadStateAdapter<ListLoadStateAdapter.ListLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: ListLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ) = ListLoadStateViewHolder.create(parent)

    class ListLoadStateViewHolder(
        private val binding: ItemFooterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.root.isVisible = loadState is LoadState.Loading || loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup): ListLoadStateViewHolder {
                val binding = ItemFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                return ListLoadStateViewHolder(binding)
            }
        }
    }
}