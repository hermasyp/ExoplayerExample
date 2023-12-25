package com.catnip.exoplayerexample.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.catnip.exoplayerexample.data.model.VideoItem
import com.catnip.exoplayerexample.databinding.ActivityMainBinding
import com.catnip.exoplayerexample.presentation.main.adapter.VideoListAdapter
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


}