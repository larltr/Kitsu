package com.angelika.kitsu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.angelika.kitsu.ProgressTarget
import com.angelika.kitsu.databinding.FooterBinding
import com.angelika.kitsu.databinding.ItemMangaBinding
import com.angelika.kitsu.models.MangaModel
import com.bumptech.glide.Glide

private const val TYPE_HEADER = 0
private const val TYPE_ITEM = 1

class MangaAdapter : PagingDataAdapter<MangaModel, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(private val binding: ItemMangaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(animeModel: MangaModel) = with(binding) {
            val progressTarget = ProgressTarget(binding.progressBarItem, binding.itemCharacterImage)
            Glide.with(itemCharacterImage).load(animeModel.attributes.posterImage.small)
                .into(progressTarget)
            itemCharacterName.text = animeModel.attributes.title.en
        }
    }

    class FooterViewHolder(private val binding: FooterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_HEADER -> {
                val mangaViewModel = holder as ViewHolder
                getItem(position)?.let { mangaViewModel.onBind(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                ViewHolder(
                    ItemMangaBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }

            TYPE_ITEM -> {
                FooterViewHolder(
                    FooterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<MangaModel>() {

        override fun areItemsTheSame(oldItem: MangaModel, newItem: MangaModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MangaModel, newItem: MangaModel): Boolean {
            return oldItem == newItem
        }
    }
}