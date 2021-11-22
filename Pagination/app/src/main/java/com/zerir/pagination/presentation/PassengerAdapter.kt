package com.zerir.pagination.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zerir.pagination.databinding.RowPassengerItemBinding
import com.zerir.pagination.domain.model.Passenger

class PassengerAdapter : PagingDataAdapter<Passenger, RecyclerView.ViewHolder>(PassengerDiffUtils()) {

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

    class ViewHolder(private val binding: RowPassengerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val context = parent.context
                val layoutInflater = LayoutInflater.from(context)
                val binding = RowPassengerItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(passenger: Passenger?) {
            binding.passenger = passenger
        }
    }

}

class PassengerDiffUtils : DiffUtil.ItemCallback<Passenger>() {

    override fun areItemsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
        return oldItem == newItem
    }

}