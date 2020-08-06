package com.example.aboutcanada.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aboutcanada.databinding.ItemMainBinding
import com.example.aboutcanada.model.FactItem

class MainAdapter(context: Context) :
    RecyclerView.Adapter<MainAdapter.ItemViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private val dataList: MutableList<FactItem> = mutableListOf()

    inner class ItemViewHolder(val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemMainBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.factItem = dataList[position]
        Glide.with(holder.binding.ivImage).load(dataList[position].imageHref)
            .into(holder.binding.ivImage)
    }

    fun setData(rows: List<FactItem>) {
        dataList.clear()
        dataList.addAll(rows)
        notifyDataSetChanged()
    }
}