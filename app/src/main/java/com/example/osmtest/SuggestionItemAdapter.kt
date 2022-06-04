package com.example.osmtest

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.osmtest.databinding.QueryItemBinding

class SuggestionItemAdapter(private val clickListener: (address: Address) -> Unit)
    : ListAdapter<Address, SuggestionItemAdapter.ViewHolder>(AddressDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder.inflateFrom(parent)

    /*private val differ = AsyncListDiffer(this, AddressDiffItemCallback())
    var addresses: List<Address>
        get() = differ.currentList
        set(value) { differ.submitList(value) }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.i("SearchApiExample", "Adapter: h1 = ${addresses.size}")
        val address = getItem(position)
        holder.bind(address, clickListener)
    }

    class ViewHolder(private val binding: QueryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = QueryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        fun bind(address: Address, clickListener: (address: Address) -> Unit) {
            binding.h1Text.text = address.display_name
            binding.h1Text.setOnClickListener { clickListener(address) }
            Log.i("SearchApiExample", "ViewHolder")
        }
    }
}

/*
package com.example.osmtest

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osmtest.databinding.QueryItemBinding

class SuggestionItemAdapter(private val listOfh1: List<String>,
                            private val listOfh2: List<String>,
                            private val clickListener: (id: Int) -> Unit) :
    RecyclerView.Adapter<SuggestionItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("SearchApiExample", "Adapter: h1 = ${listOfh1.size}, h2 = ${listOfh2.size}")
        val h1 = listOfh1[position]
        var h2 = ""
        if (listOfh2.isNotEmpty() && position < listOfh2.size) {
            h2 = listOfh2[position]
        }
        holder.bind(h1, h2, position, clickListener)
    }

    class ViewHolder(private val binding: QueryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = QueryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(h1: String, h2: String, id: Int, clickListener: (id: Int) -> Unit) {
            binding.h1Text.text = h1
            binding.h2Text.text = h2
            binding.h1Text.setOnClickListener { clickListener(id) }
        }
    }

    override fun getItemCount(): Int {
        return listOfh1.size
    }
}*/
