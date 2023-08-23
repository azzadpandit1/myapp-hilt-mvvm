package com.example.myapp.ui.fregment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.myapp.R
import com.example.myapp.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.Util

class PlayerFragment : Fragment() {

    private val binding by lazy { FragmentPlayerBinding.inflate(layoutInflater) }
    private var simpleExoPlayer: ExoPlayer? = null
    private var videoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoUrl = it.getString("url")
            Log.e("TAG", "onCreate: url " + it.getString("url"))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initializePlayer() {


        if (videoUrl?.endsWith("mp4") == true){
            Log.e("TAG", "- > initializePlayer: video url"+videoUrl )
            initVideo()
        }else if (videoUrl?.endsWith("mp3") == true){
            Log.e("TAG", "- > initializePlayer: mp3 url"+videoUrl )
            initVideo()
        }else{
            Log.e("TAG", "initializePlayer: "+videoUrl )
            binding.playerView.visibility = View.GONE

            Glide.with(requireContext())
                .asGif()
                .load(videoUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.imageView)

        }



    }

    private fun initVideo() {

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(requireContext())
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl.let { it }!!.toUri()))

        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = ExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        simpleExoPlayer?.addMediaSource(mediaSource)
        simpleExoPlayer?.playWhenReady = true
        simpleExoPlayer?.prepare()
        binding.playerView.player = simpleExoPlayer
        binding.playerView.requestFocus()

        binding.playerView.player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(@Player.State state: Int) {
                when (state) {
                    Player.STATE_READY -> {
                        binding.progressBar.isVisible = false
                        // The player is able to immediately play from its current position. The player will be playing if getPlayWhenReady() is true, and paused otherwise.
                    }
                    Player.STATE_BUFFERING -> {
                        binding.progressBar.isVisible = true
                        // The player is not able to immediately play the media, but is doing work toward being able to do so. This state typically occurs when the player needs to buffer more data before playback can start.
                    }
                    Player.STATE_IDLE -> {
                        // The player is idle, meaning it holds only limited resources.The player must be prepared before it will play the media.
                        binding.progressBar.isVisible = true
                    }
                    Player.STATE_ENDED -> {
                        // The player has finished playing the media.
                        binding.progressBar.isVisible = false
                    }
                    else -> {
                        // Other things
                        binding.progressBar.isVisible = false
                    }
                }
            }
        })

/*
        val desiredPosition = simpleExoPlayer.duration / 2.toLong() // 50%
        simpleExoPlayer.seekTo(desiredPosition)
*/





    }


    //mp3 player setup
   /* private fun prepareMediaPlayer() {
        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(requireContext())

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(RADIO_URL))

        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = ExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        simpleExoPlayer.addMediaSource(mediaSource)
        simpleExoPlayer.prepare()
    }*/

    private fun releasePlayer() {
        simpleExoPlayer?.release()
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23) initializePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Util.SDK_INT > 23) releasePlayer()
    }

}