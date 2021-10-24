package com.example.swissborgtest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.swissborgtest.databinding.OrderBookItemBinding
import com.example.swissborgtest.model.OrderBook

class OrderBookAdapter : ListAdapter<OrderBook, OrderBookAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<OrderBook>() {
        override fun areItemsTheSame(oldItem: OrderBook, newItem: OrderBook): Boolean {
            return oldItem.price == newItem.price
        }

        override fun areContentsTheSame(oldItem: OrderBook, newItem: OrderBook): Boolean {
            return oldItem.amount == newItem.amount
        }
    }

    class ViewHolder(private val binding: OrderBookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderBook: OrderBook) {
            binding.orderBook = orderBook
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OrderBookItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}