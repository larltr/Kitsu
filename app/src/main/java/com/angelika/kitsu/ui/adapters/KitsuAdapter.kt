package com.angelika.kitsu.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.angelika.kitsu.ProgressTarget
import com.angelika.kitsu.databinding.ItemKitsuBinding
import com.angelika.kitsu.models.KitsuModel
import com.bumptech.glide.Glide

class KitsuAdapter : PagingDataAdapter<KitsuModel, KitsuAdapter.ViewHolder>(DiffUtilCallback()) {

    private val NETWORK_VIEW_TYPE = 1
    val WALLPAPER_VIEW_TYPE = 2

    inner class ViewHolder(private val binding: ItemKitsuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(animeModel: KitsuModel) = with(binding) {
            val progressTarget = ProgressTarget(binding.progressBarItem, binding.itemCharacterImage)
            Glide.with(itemCharacterImage).load(animeModel.attributes.posterImage.small)
                .into(progressTarget)
            itemCharacterName.text = animeModel.attributes.title.enJp
        }
    }

    override fun onBindViewHolder(holder: KitsuAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitsuAdapter.ViewHolder {
        return ViewHolder(
            ItemKitsuBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent,
                false
            )
        )
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<KitsuModel>() {

        override fun areItemsTheSame(oldItem: KitsuModel, newItem: KitsuModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: KitsuModel, newItem: KitsuModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            NETWORK_VIEW_TYPE
        } else {
            WALLPAPER_VIEW_TYPE
        }
    }
}