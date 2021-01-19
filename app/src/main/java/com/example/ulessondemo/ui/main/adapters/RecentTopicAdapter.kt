package com.example.ulessondemo.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ulessondemo.R
import com.example.ulessondemo.databinding.ItemLayoutRecentTopicBinding
import com.example.ulessondemo.model.LessonWithSubjectAndChapter
import com.example.ulessondemo.util.Util
import com.example.ulessondemo.util.VideoThumbnail
import kotlin.math.min


class RecentTopicAdapter(private val onLessonCLicked: (LessonWithSubjectAndChapter) -> Unit) :
    ListAdapter<LessonWithSubjectAndChapter, RecentTopicAdapter.ViewHolder>(DIFF_UTIL) {

    private var showingMore = false

    private var originalList = emptyList<LessonWithSubjectAndChapter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutRecentTopicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun submitList(list: List<LessonWithSubjectAndChapter>?) {
        originalList = list.orEmpty()
        super.submitList(getLessonList())
    }

    fun showMore(showMore: Boolean) {
        showingMore = showMore
        super.submitList(getLessonList())
    }

    private fun getLessonList() = if (showingMore) originalList else originalList.subList(
        0,
        min(1, originalList.lastIndex) + 1
    )

    inner class ViewHolder(private val binding: ItemLayoutRecentTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onLessonCLicked(getItem(bindingAdapterPosition))
            }
        }

        fun bind() {
            with(getItem(bindingAdapterPosition)) {
                binding.lessonName.text = lesson.name
                binding.subjectName.text = subject.name
                binding.subjectName.setTextColor(
                    ResourcesCompat.getColor(
                        binding.root.resources,
                        Util.getColorForIndex(subject.drawableAndColorIndex),
                        binding.root.context.theme
                    )
                )
                binding.icPlay.setImageResource(Util.getPlayDrawableForIndex(subject.drawableAndColorIndex))
                Glide.with(binding.thumbnail).load(VideoThumbnail(lesson.mediaUrl))
                    .placeholder(R.color.placeholder).into(binding.thumbnail)
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
