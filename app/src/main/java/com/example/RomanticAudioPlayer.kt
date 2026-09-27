package com.example

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

/**
 * Romantic Audio Player:
 * 1. Plays a soothing, real-time synthesized acoustic music-box chime melody
 *    out-of-the-box (no external MP3 file or permissions needed).
 * 2. Allows seamlessly playing any custom MP3/Audio file picked from the device
 *    or placed in the project.
 * 3. Does NOT autoplay without explicit user interaction (per user requirement).
 */
class RomanticAudioPlayer(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _currentTrackTitle = MutableStateFlow(BirthdayConfig.MUSIC_TITLE)
    val currentTrackTitle: StateFlow<String> = _currentTrackTitle.asStateFlow()

    private val _customUri = MutableStateFlow<Uri?>(null)
    val customUri: StateFlow<Uri?> = _customUri.asStateFlow()

    private var mediaPlayer: MediaPlayer? = null
    private var synthJob: Job? = null
    private var progressJob: Job? = null
    private var audioTrack: AudioTrack? = null

    // Notes for romantic music-box melody (Happy Birthday / Love melody harmonics)
    // Frequencies in Hz: C4, D4, E4, F4, G4, A4, B4, C5, etc.
    private val melodyNotes = listOf(
        Pair(261.63, 400L), // C4
        Pair(261.63, 400L), // C4
        Pair(293.66, 800L), // D4
        Pair(261.63, 800L), // C4
        Pair(349.23, 800L), // F4
        Pair(329.63, 1400L),// E4

        Pair(261.63, 400L), // C4
        Pair(261.63, 400L), // C4
        Pair(293.66, 800L), // D4
        Pair(261.63, 800L), // C4
        Pair(392.00, 800L), // G4
        Pair(349.23, 1400L),// F4

        Pair(261.63, 400L), // C4
        Pair(261.63, 400L), // C4
        Pair(523.25, 800L), // C5
        Pair(440.00, 800L), // A4
        Pair(349.23, 800L), // F4
        Pair(329.63, 800L), // E4
        Pair(293.66, 1200L),// D4

        Pair(466.16, 400L), // Bb4
        Pair(466.16, 400L), // Bb4
        Pair(440.00, 800L), // A4
        Pair(349.23, 800L), // F4
        Pair(392.00, 800L), // G4
        Pair(349.23, 1600L) // F4
    )

    fun togglePlay() {
        if (_isPlaying.value) {
            pause()
        } else {
            play()
        }
    }

    fun play() {
        if (_customUri.value != null) {
            playCustomUri(_customUri.value!!)
        } else {
            playSynthesizedMelody()
        }
    }

    fun pause() {
        _isPlaying.value = false
        stopCustomPlayer()
        stopSynthesizer()
        progressJob?.cancel()
    }

    fun setCustomAudio(uri: Uri, fileName: String? = null) {
        pause()
        _customUri.value = uri
        _currentTrackTitle.value = fileName ?: "Custom Song ❤️"
    }

    fun resetToBuiltInMusic() {
        pause()
        _customUri.value = null
        _currentTrackTitle.value = BirthdayConfig.MUSIC_TITLE
    }

    private fun playCustomUri(uri: Uri) {
        try {
            stopCustomPlayer()
            stopSynthesizer()

            mediaPlayer = MediaPlayer().apply {
                setDataSource(context, uri)
                prepare()
                isLooping = true
                start()
            }
            _isPlaying.value = true

            startProgressTrackerForMediaPlayer()
        } catch (e: Exception) {
            // Fallback to synthesized melody if custom file fails
            _customUri.value = null
            _currentTrackTitle.value = BirthdayConfig.MUSIC_TITLE
            playSynthesizedMelody()
        }
    }

    private fun startProgressTrackerForMediaPlayer() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive && _isPlaying.value) {
                val mp = mediaPlayer
                if (mp != null && mp.isPlaying) {
                    val current = mp.currentPosition.toFloat()
                    val total = mp.duration.toFloat().coerceAtLeast(1f)
                    _progress.value = (current / total).coerceIn(0f, 1f)
                }
                delay(200)
            }
        }
    }

    private fun playSynthesizedMelody() {
        stopCustomPlayer()
        stopSynthesizer()
        _isPlaying.value = true

        val sampleRate = 22050
        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(4096)

        try {
            audioTrack = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()
            } else {
                @Suppress("DEPRECATION")
                AudioTrack(
                    AudioManager.STREAM_MUSIC,
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferSize,
                    AudioTrack.MODE_STREAM
                )
            }

            audioTrack?.play()

            synthJob = scope.launch(Dispatchers.IO) {
                var noteIdx = 0
                val totalNotes = melodyNotes.size
                while (isActive && _isPlaying.value) {
                    val (freq, durMs) = melodyNotes[noteIdx]
                    _progress.value = noteIdx.toFloat() / totalNotes.toFloat()

                    // Generate gentle music-box tone (sine wave with bell-like exponential decay)
                    val numSamples = ((durMs * sampleRate) / 1000).toInt()
                    val pcmData = ShortArray(numSamples)

                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        // Primary bell tone + soft octave overtone
                        val primary = sin(2.0 * PI * freq * t)
                        val overtone = 0.3 * sin(2.0 * PI * freq * 2.0 * t)
                        // Exponential bell envelope (music box ding)
                        val envelope = exp(-4.0 * (i.toDouble() / numSamples))
                        val sample = ((primary + overtone) * envelope * 22000.0).toInt()
                        pcmData[i] = sample.coerceIn(-32767, 32767).toShort()
                    }

                    audioTrack?.write(pcmData, 0, numSamples)

                    noteIdx = (noteIdx + 1) % melodyNotes.size
                    // Small silence between music-box plucks
                    delay(50)
                }
            }
        } catch (e: Exception) {
            _isPlaying.value = false
        }
    }

    private fun stopCustomPlayer() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (ignored: Exception) {}
        mediaPlayer = null
    }

    private fun stopSynthesizer() {
        synthJob?.cancel()
        synthJob = null
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (ignored: Exception) {}
        audioTrack = null
    }

    fun release() {
        pause()
    }
}
