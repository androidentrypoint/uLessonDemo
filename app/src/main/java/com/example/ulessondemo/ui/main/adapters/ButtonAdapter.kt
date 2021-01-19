package com.example.ulessondemo.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ulessondemo.databinding.ItemLayoutShowMoreBtnBinding

class ButtonAdapter(private val onButtonClick: (showMore: Boolean) -> Unit) :
    RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

    private var showingMore = false
    private var showButton = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutShowMoreBtnBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = if (showButton) 1 else 0

    fun showButton(showButton: Boolean) {
        this.showButton = showButton
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLayoutShowMoreBtnBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.showMore.setOnClickListener {
                showingMore = !showingMore
                onButtonClick(showingMore)
                notifyDataSetChanged()
            }
        }

        fun bind() {
            binding.showMore.text = if (showingMore) "SHOW LESS" else "SHOW MORE"
        }
    }
}