package com.example.ulessondemo.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ulessondemo.R
import com.example.ulessondemo.databinding.ItemLayoutLessonBinding
import com.example.ulessondemo.model.LessonWithSubjectAndChapter

class LessonAdapter(private val onLessonCLicked: (LessonWithSubjectAndChapter) -> Unit) :
    ListAdapter<LessonWithSubjectAndChapter, LessonAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutLessonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(private val binding: ItemLayoutLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onLessonCLicked(getItem(bindingAdapterPosition))
            }
        }

        fun bind() {
            with(getItem(bindingAdapterPosition)) {
                binding.name.text = lesson.name
                Glide.with(binding.icon).load(lesson.iconUrl).placeholder(R.color.placeholder)
                    .into(binding.icon)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<LessonWithSubjectAndChapter>() {
            override fun areItemsTheSame(
                oldItem: LessonWithSubjectAndChapter,
                newItem: LessonWithSubjectAndChapter
            ): Boolean {
                return oldItem.lesson.id == newItem.lesson.id
            }

            override fun areContentsTheSame(
                oldItem: LessonWithSubjectAndChapter,
                newItem: LessonWithSubjectAndChapter
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
