package com.angelika.kitsu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angelika.kitsu.databinding.ItemAnimeBinding
import com.angelika.kitsu.models.MangaModel
import com.bumptech.glide.Glide

class MangaAdapter : ListAdapter<MangaModel, MangaAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(animeModel: MangaModel) = with(binding) {
            Glide.with(itemCharacterImage).load(animeModel.attributes.posterImage.original)
                .into(itemCharacterImage)
            itemCharacterName.text = animeModel.attributes.title.en
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAnimeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
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