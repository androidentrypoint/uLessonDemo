package com.example.ulessondemo.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ulessondemo.databinding.ItemLayoutSubjectGridBinding
import com.example.ulessondemo.model.SubjectWithChapters
import com.example.ulessondemo.util.GridSpacingItemDecoration
import com.example.ulessondemo.util.px

class SubjectGridAdapter(private val onSubjectCLicked: (SubjectWithChapters) -> Unit) :
    RecyclerView.Adapter<SubjectGridAdapter.ViewHolder>() {

    private var subjects = emptyList<SubjectWithChapters>()

    fun addSubjects(subjects: List<SubjectWithChapters>) {
        this.subjects = subjects
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutSubjectGridBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1

    inner class ViewHolder(private val binding: ItemLayoutSubjectGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val adapter = SubjectAdapter(onSubjectCLicked)

        init {
            binding.subjectsRv.isNestedScrollingEnabled = false
            binding.subjectsRv.adapter = adapter
            binding.subjectsRv.addItemDecoration(
                GridSpacingItemDecoration(2, 10.px, 11.px, 20.px, 20.px)
            )
        }

        fun bind() {
            adapter.submitList(subjects)
        }
    }
}
