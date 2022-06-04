package com.example.osmtest

import androidx.recyclerview.widget.DiffUtil

class AddressDiffItemCallback : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.display_name == newItem.display_name
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem == newItem
    }
}