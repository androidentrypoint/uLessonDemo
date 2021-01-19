package com.example.ulessondemo.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ulessondemo.databinding.ItemLayoutSubjectBinding
import com.example.ulessondemo.model.SubjectWithChapters
import com.example.ulessondemo.util.Util

class SubjectAdapter(private val onSubjectCLicked: (SubjectWithChapters) -> Unit) :
    ListAdapter<SubjectWithChapters, SubjectAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutSubjectBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(private val binding: ItemLayoutSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onSubjectCLicked(getItem(bindingAdapterPosition))
            }
        }

        fun bind() {
            with(getItem(bindingAdapterPosition)) {
                binding.name.text = subject.name
                binding.main.setBackgroundResource(Util.getBgDrawableForIndex(subject.drawableAndColorIndex))
                Glide.with(binding.icon).load(subject.iconUrl).into(binding.icon)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<SubjectWithChapters>() {
            override fun areItemsTheSame(
                oldItem: SubjectWithChapters,
                newItem: SubjectWithChapters
            ): Boolean {
                return oldItem.subject.id == newItem.subject.id
            }

            override fun areContentsTheSame(
                oldItem: SubjectWithChapters,
                newItem: SubjectWithChapters
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
