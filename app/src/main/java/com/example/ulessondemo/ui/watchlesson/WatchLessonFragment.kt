package com.example.ulessondemo.ui.watchlesson

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ulessondemo.R
import com.example.ulessondemo.databinding.WatchLessonFragmentBinding
import com.example.ulessondemo.util.viewbinding.viewBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WatchLessonFragment : Fragment(R.layout.watch_lesson_fragment) {

    private var player: SimpleExoPlayer? = null

    private val viewModel: WatchLessonViewModel by viewModels()
    private val binding by viewBinding(WatchLessonFragmentBinding::bind)
    private val args by navArgs<WatchLessonFragmentArgs>()

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    private val listener = object : Player.EventListener {
        override fun onPlaybackStateChanged(state: Int) {
            super.onPlaybackStateChanged(state)
            when (state) {
                Player.STATE_BUFFERING -> {
                    binding.progressBar.isVisible = true
                }
                Player.STATE_ENDED -> {
                    binding.progressBar.isVisible = false
                }
                Player.STATE_IDLE -> {
                    binding.progressBar.isVisible = false
                }
                Player.STATE_READY -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chapterName.text = args.lesson.chapter.name
        binding.lessonName.text = args.lesson.lesson.name
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initializePlayer() {
        if (player != null) return
        player = SimpleExoPlayer.Builder(requireContext())
            .setTrackSelector(DefaultTrackSelector(requireContext()).apply {
                setParameters(buildUponParameters().setMaxVideoSizeSd())
            })
            .build()
        player?.setMediaItem(
//            MediaItem.fromUri(args.lesson.lesson.mediaUrl)
            MediaItem.Builder()
                .setUri(args.lesson.lesson.mediaUrl)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build()
        )
        player?.addListener(listener)
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        binding.player.player = player
        player?.prepare()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.removeListener(listener)
            player!!.release()
            player = null
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }
}