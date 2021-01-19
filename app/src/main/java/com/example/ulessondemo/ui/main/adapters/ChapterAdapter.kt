package com.example.ulessondemo.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ulessondemo.databinding.ItemChapterLayoutBinding
import com.example.ulessondemo.model.ChapterWithLessons
import com.example.ulessondemo.model.LessonWithSubjectAndChapter
import com.example.ulessondemo.model.Subject

class ChapterAdapter(
    private val subject: Subject,
    private val onLessonCLicked: (LessonWithSubjectAndChapter) -> Unit
) : ListAdapter<ChapterWithLessons, ChapterAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChapterLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(private val binding: ItemChapterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val adapter = LessonAdapter(onLessonCLicked)

        init {
            binding.lessonsRv.adapter = adapter
        }

        fun bind() {
            with(getItem(bindingAdapterPosition)) {
                binding.name.text = chapter.name
                adapter.submitList(lessons.map {
                    LessonWithSubjectAndChapter(
                        it,
                        chapter,
                        subject
                    )
                })
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ChapterWithLessons>() {
            override fun areItemsTheSame(
                oldItem: ChapterWithLessons,
                newItem: ChapterWithLessons
            ): Boolean {
                return oldItem.chapter.id == newItem.chapter.id
            }

            override fun areContentsTheSame(
                oldItem: ChapterWithLessons,
                newItem: ChapterWithLessons
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
