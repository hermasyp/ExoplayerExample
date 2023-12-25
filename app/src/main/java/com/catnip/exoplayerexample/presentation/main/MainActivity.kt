package com.catnip.exoplayerexample.presentation.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.catnip.exoplayerexample.R
import com.catnip.exoplayerexample.data.model.VideoItem
import com.catnip.exoplayerexample.databinding.ActivityMainBinding
import com.catnip.exoplayerexample.presentation.main.adapter.VideoListAdapter
import com.catnip.exoplayerexample.utils.setFullScreen
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter: VideoListAdapter by lazy {
        VideoListAdapter { videoItem ->
            playVideo(videoItem)
        }
    }

    private val viewModel: MainViewModel by viewModel()

    private fun playVideo(videoItem: VideoItem) {
        viewModel.setPlayedVideo(binding.playerView, videoItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupVideoList()
        observePlayedVideo()
        observeFullScreen()
        viewModel.bindPlayer(lifecycle)
    }

    private fun observePlayedVideo() {
        viewModel.playedVideo.observe(this) {
            binding.tvVideoTitle.text = it.title
            binding.tvVideoAuthor.text = it.author
        }
    }

    private fun setupVideoList() {
        binding.rvPlaylist.adapter = adapter
        viewModel.getVideos().let {
            adapter.submitData(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.releasePlayer(lifecycle)
    }

    private fun observeFullScreen() {
        viewModel.isFullScreenLiveData.observe(this) { isFullScreen ->
            if (isFullScreen) {
                Toast.makeText(this, "FullScreen", Toast.LENGTH_SHORT).show()
                enterFullScreen()
            } else {
                Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show()
                closeFullScreen()
            }
        }
    }

    private fun enterFullScreen() {
        this.setFullScreen(true)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        hideUI()
    }

    private fun showUI() {
        binding.llPlayingInfo.isVisible = true
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.clear(R.id.player_view,ConstraintSet.BOTTOM)
        constraintSet.applyTo(binding.root)
        val param = binding.playerView.layoutParams as ConstraintLayout.LayoutParams
        param.dimensionRatio = "16:9"
        binding.playerView.layoutParams = param
    }

    private fun hideUI() {
        binding.llPlayingInfo.isVisible = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.root)
        constraintSet.connect(R.id.player_view,ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,0)
        constraintSet.applyTo(binding.root)
        val param = binding.playerView.layoutParams as ConstraintLayout.LayoutParams
        param.dimensionRatio = ""
        binding.playerView.layoutParams = param
    }

    private fun closeFullScreen() {
        this.setFullScreen(false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        showUI()
    }


}