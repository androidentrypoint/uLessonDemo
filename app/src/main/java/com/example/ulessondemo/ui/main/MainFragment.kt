package com.example.ulessondemo.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.ulessondemo.R
import com.example.ulessondemo.databinding.MainFragmentBinding
import com.example.ulessondemo.ui.main.adapters.ButtonAdapter
import com.example.ulessondemo.ui.main.adapters.HeaderAdapter
import com.example.ulessondemo.ui.main.adapters.RecentTopicAdapter
import com.example.ulessondemo.ui.main.adapters.SubjectGridAdapter
import com.example.ulessondemo.util.NetworkStatus
import com.example.ulessondemo.util.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(MainFragmentBinding::bind)

    private val subjectGridAdapter = SubjectGridAdapter {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToLessonsFragment(it))
    }

    private val recentTopicHeaderAdapter = HeaderAdapter()
    private val recentTopicAdapter = RecentTopicAdapter {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToWatchLessonFragment(it)
        )
    }
    private val buttonAdapter = ButtonAdapter {
        recentTopicAdapter.showMore(it)
    }

    private val concatAdapter = ConcatAdapter(
        subjectGridAdapter,
        recentTopicHeaderAdapter,
        recentTopicAdapter,
        buttonAdapter
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeRv.adapter = concatAdapter
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkStatus.Success -> {
                    binding.swipeToRefresh.isRefreshing = false
                    subjectGridAdapter.addSubjects(it.data.subjects)
                    recentTopicAdapter.submitList(it.data.recentlyWatchedLessons)
                    buttonAdapter.showButton(it.data.recentlyWatchedLessons.size > 2)
                    recentTopicHeaderAdapter.showHeader(it.data.recentlyWatchedLessons.isNotEmpty())
                }
                is NetworkStatus.Loading -> {
                    binding.swipeToRefresh.isRefreshing = true
                    subjectGridAdapter.addSubjects(it.data?.subjects.orEmpty())
                    recentTopicAdapter.submitList(it.data?.recentlyWatchedLessons.orEmpty())
                    buttonAdapter.showButton(it.data?.recentlyWatchedLessons.orEmpty().size > 2)
                    recentTopicHeaderAdapter.showHeader(
                        it.data?.recentlyWatchedLessons.isNullOrEmpty().not()
                    )
                }
                is NetworkStatus.Error -> {
                    binding.swipeToRefresh.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}