package com.example.ulessondemo.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ulessondemo.databinding.ItemLayoutRecentTopicHeaderBinding

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    private var showHeader = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutRecentTopicHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    fun showHeader(showHeader: Boolean) {
        this.showHeader = showHeader
        notifyDataSetChanged()
    }

    override fun getItemCount() = if (showHeader) 1 else 0

    inner class ViewHolder(binding: ItemLayoutRecentTopicHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)
}