package com.example.ulessondemo.ui.chapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ulessondemo.R
import com.example.ulessondemo.databinding.LessonsFragmentBinding
import com.example.ulessondemo.ui.main.adapters.ChapterAdapter
import com.example.ulessondemo.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonsFragment : Fragment(R.layout.lessons_fragment) {

    private val viewModel: LessonsViewModel by viewModels()
    private val binding by viewBinding(LessonsFragmentBinding::bind)

    private val args by navArgs<LessonsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = args.subjectWithChapters.subject.name
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.chaptersRv.adapter = ChapterAdapter(args.subjectWithChapters.subject) {
            findNavController().navigate(
                LessonsFragmentDirections.actionLessonsFragmentToWatchLessonFragment(it)
            )
        }.apply {
            submitList(args.subjectWithChapters.chapters)
        }
    }
}