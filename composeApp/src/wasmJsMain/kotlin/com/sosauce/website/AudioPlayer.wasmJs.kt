@file:OptIn(ExperimentalWasmJsInterop::class)

package com.sosauce.website



import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.nameWithoutExtension
import kotlinx.browser.document
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.w3c.dom.HTMLAudioElement

actual fun getPlayer(): AudioPlayer = WasmPlayer()

// https://developer.mozilla.org/fr/docs/Web/API/HTMLAudioElement
class WasmPlayer : AudioPlayer {

    private val _state: MutableStateFlow<AudioState> = MutableStateFlow(AudioState())
    override val state: StateFlow<AudioState> = _state.asStateFlow()
    private val audioElement = (document.createElement("audio") as HTMLAudioElement).apply {

        id = "audio"
        document.body?.appendChild(this)


        onloadstart = {
            _state.update {
                it.copy(
                    isBuffering = true
                )
            }
        }
        onratechange = {
            _state.update {
                it.copy(
                    rate = playbackRate
                )
            }
        }

        onplay = {
            _state.update {
                it.copy(
                    isPlaying = true
                )
            }
        }

        onpause = {
            _state.update {
                it.copy(
                    isPlaying = false
                )
            }
        }

        onloadeddata = {
            _state.update {
                it.copy(
                    isBuffering = false,
                    name = null,
                    durationMillis = duration.toInt() * 1000
                )
            }
        }

        ontimeupdate = {
            _state.update {
                it.copy(
                    positionInMillis = currentTime.toInt() * 1000
                )
            }
        }

    }

    override fun playOrPause() {
        if (audioElement.paused) {
            audioElement.play()
        } else audioElement.pause()
    }

    override fun toggleLoop() {
        audioElement.loop = !audioElement.loop
        _state.update {
            it.copy(
                isLooping = audioElement.loop
            )
        }
    }

    override fun changeRate(rate: Float) {
        audioElement.playbackRate = rate.toDouble()

    }

    override fun loadFile(file: PlatformFile) {

        val fileString = file.getUri()


        _state.update {
            it.copy(
                isLoaded = true,
                name = file.nameWithoutExtension
            )
        }
        audioElement.src = fileString
        audioElement.play()


    }

    override fun seekTo(positionInMillis: Int) {
        val seconds = positionInMillis.toDouble() / 1000
        audioElement.currentTime = seconds
    }

    override fun fastForward() {
        audioElement.currentTime += 5
    }

    override fun fastRewind() {
        audioElement.currentTime -= 5
    }
}





