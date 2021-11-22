package com.zerir.pagination.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zerir.pagination.databinding.RowPlanetItemBinding
import com.zerir.pagination.domain.model.Planet

class PlanetAdapter : PagingDataAdapter<Planet, RecyclerView.ViewHolder>(ImageDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position)
                holder.bind(item)
            }
        }
    }

    class ViewHolder(private val binding: RowPlanetItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val context = parent.context
                val layoutInflater = LayoutInflater.from(context)
                val binding = RowPlanetItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(planet: Planet?) {
            binding.planet = planet
        }
    }

}

class ImageDiffUtils : DiffUtil.ItemCallback<Planet>() {

    override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
        return oldItem == newItem
    }

}