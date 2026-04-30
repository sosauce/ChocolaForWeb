package com.sosauce.website

import androidx.compose.runtime.Composable
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AudioPlayer {

    val state: StateFlow<AudioState>
    fun playOrPause()
    fun loadFile(file: PlatformFile)

    fun seekTo(positionInMillis: Int)

    fun fastForward()
    fun fastRewind()

    fun changeRate(rate: Float)

    fun toggleLoop()
}

expect fun getPlayer(): AudioPlayer

data class AudioState(
    val isLoaded: Boolean = false,
    val name: String? = null,
    val durationMillis: Int = 0,
    val positionInMillis: Int = 0,
    val isBuffering: Boolean = false,
    val isPlaying: Boolean = false,
    val rate: Double = 1.0,
    val isLooping: Boolean = false
)


