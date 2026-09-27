package com.example

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CardBorderPink
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.PetalPink
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RomanticGold
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.SoftPink
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark
import kotlin.math.sin

@Composable
fun MusicScreen(
    modifier: Modifier = Modifier,
    player: RomanticAudioPlayer,
    onContinue: () -> Unit
) {
    val isPlaying by player.isPlaying.collectAsState()
    val progress by player.progress.collectAsState()
    val trackTitle by player.currentTrackTitle.collectAsState()
    val customUri by player.customUri.collectAsState()

    // File Picker for user's own MP3 or audio file
    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val fileName = uri.lastPathSegment?.substringAfterLast('/') ?: "My Love Song.mp3"
            player.setCustomAudio(uri, fileName)
        }
    }

    // Vinyl spinning rotation
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl_spin")
    val vinylAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "vinyl_rotation"
    )

    // Dancing equalizer bars animation
    val eqWave by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "eq_wave"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header
        Text(
            text = "A Romantic Melody 🎶",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RoseDark
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Press Play to fill this moment with music ❤️",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondaryDark,
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Main Player Turntable Card
        Card(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .shadow(12.dp, RoundedCornerShape(28.dp))
                .border(2.dp, CardBorderPink, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Vinyl Record
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .shadow(14.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFF1A1A1A))
                        .rotate(if (isPlaying) vinylAngle else 0f),
                    contentAlignment = Alignment.Center
                ) {
                    // Vinyl grooves drawing
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val maxR = size.width / 2

                        // Grooves
                        for (r in listOf(0.35f, 0.45f, 0.55f, 0.65f, 0.75f, 0.85f)) {
                            drawCircle(
                                color = Color(0xFF333333),
                                radius = maxR * r,
                                center = center,
                                style = Stroke(width = 1.5f)
                            )
                        }
                    }

                    // Center Pink Label
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(SoftPink, RosePrimary)
                                )
                            )
                            .border(2.dp, RomanticGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Vinyl Heart",
                            tint = PureWhite,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    // Center spindle hole
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF111111))
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Equalizer Visualizer Bars
                Row(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val barCount = 12
                    for (i in 0 until barCount) {
                        val barHeightFactor = if (isPlaying) {
                            (0.3f + 0.7f * ((sin(eqWave + (i * 0.8f)) + 1f) / 2f)).coerceIn(0.2f, 1f)
                        } else {
                            0.15f
                        }
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .height((30 * barHeightFactor).dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (i % 2 == 0) RosePrimary else RomanticGold
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Track Title & Artist
                Text(
                    text = trackTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = RoseDark
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (customUri != null) "Custom Audio Track 🎧" else BirthdayConfig.MUSIC_ARTIST,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextSecondaryDark,
                        fontStyle = FontStyle.Italic
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Track Progress Bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = RosePrimary,
                    trackColor = PetalPink,
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Play / Pause Master Button
                Button(
                    onClick = { player.togglePlay() },
                    modifier = Modifier
                        .size(68.dp)
                        .testTag("play_pause_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                    shape = CircleShape,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = PureWhite,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Replace Music / Placeholder Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            audioPickerLauncher.launch("audio/*")
                        },
                        modifier = Modifier.testTag("pick_music_file_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FolderOpen,
                            contentDescription = "Pick Custom Song",
                            tint = RosePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Choose Your Song (.mp3) 📁",
                            color = RosePrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (customUri != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { player.resetToBuiltInMusic() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset Music",
                                tint = TextSecondaryDark
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Continue to Final Surprise Button
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(56.dp)
                .testTag("continue_to_final_button"),
            colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
            shape = RoundedCornerShape(28.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = "One More Thing... ✨ →",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PureWhite,
                    fontSize = 17.sp
                )
            )
        }
    }
}
