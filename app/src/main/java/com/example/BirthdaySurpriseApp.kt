package com.example

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.PetalPink
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RomanticGold
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdaySurpriseApp() {
    val context = LocalContext.current
    val preferences = remember { BirthdayPreferences(context) }
    val audioPlayer = remember { RomanticAudioPlayer(context) }

    // Clean up audio player when composable leaves composition
    DisposableEffect(Unit) {
        onDispose {
            audioPlayer.release()
        }
    }

    // Step state: 0: Intro, 1: Reveal, 2: Memories, 3: Letter, 4: Music, 5: Final
    var currentStep by remember { mutableIntStateOf(0) }
    var showCustomizeSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Dynamic preferences states
    var girlfriendName by remember { mutableStateOf(preferences.girlfriendName) }
    var senderName by remember { mutableStateOf(preferences.senderName) }
    var letterBody by remember { mutableStateOf(preferences.letterBody) }

    val isMusicPlaying by audioPlayer.isPlaying.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        containerColor = CreamBackground,
        topBar = {
            // Elegant Romantic Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step Indicator Pills
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (step in 0..5) {
                        val isCurrent = step == currentStep
                        val isPassed = step < currentStep
                        Box(
                            modifier = Modifier
                                .height(6.dp)
                                .width(if (isCurrent) 24.dp else 8.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    when {
                                        isCurrent -> RosePrimary
                                        isPassed -> PetalPink
                                        else -> Color(0xFFE0D4D6)
                                    }
                                )
                        )
                    }
                }

                // Right Side Controls: Mini Music Controller & Customizer button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Mini Music Player Pill (appears if music is loaded or playing)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PureWhite,
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                audioPlayer.togglePlay()
                            }
                            .testTag("mini_music_pill")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isMusicPlaying) Icons.Default.Pause else Icons.Default.MusicNote,
                                contentDescription = "Toggle Music",
                                tint = if (isMusicPlaying) RosePrimary else RoseDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isMusicPlaying) "Music On 🎵" else "Music",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isMusicPlaying) RosePrimary else RoseDark
                                )
                            )
                        }
                    }

                    // Discreet Edit Button
                    IconButton(
                        onClick = { showCustomizeSheet = true },
                        modifier = Modifier
                            .size(34.dp)
                            .shadow(2.dp, CircleShape)
                            .clip(CircleShape)
                            .background(PureWhite)
                            .testTag("open_customize_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Customize Birthday Surprise",
                            tint = RosePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Ambient Floating Hearts in Background
            FloatingHeartsBackground(heartCount = 14)

            // Step Transitions with smooth slide & fade
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInHorizontally { width -> width / 3 } + fadeIn())
                            .togetherWith(slideOutHorizontally { width -> -width / 3 } + fadeOut())
                    } else {
                        (slideInHorizontally { width -> -width / 3 } + fadeIn())
                            .togetherWith(slideOutHorizontally { width -> width / 3 } + fadeOut())
                    }
                },
                label = "step_transition"
            ) { targetStep ->
                when (targetStep) {
                    0 -> IntroScreen(
                        girlfriendName = girlfriendName,
                        onOpenSurprise = { currentStep = 1 }
                    )
                    1 -> BirthdayRevealScreen(
                        girlfriendName = girlfriendName,
                        onContinue = { currentStep = 2 }
                    )
                    2 -> MemoriesScreen(
                        preferences = preferences,
                        onContinue = { currentStep = 3 }
                    )
                    3 -> EnvelopeLetterSection(
                        letterMessage = letterBody,
                        girlfriendName = girlfriendName,
                        senderName = senderName,
                        onContinue = { currentStep = 4 }
                    )
                    4 -> MusicScreen(
                        player = audioPlayer,
                        onContinue = { currentStep = 5 }
                    )
                    5 -> FinalSurpriseScreen(
                        girlfriendName = girlfriendName,
                        senderName = senderName,
                        onReplay = { currentStep = 0 },
                        onOpenCustomize = { showCustomizeSheet = true }
                    )
                }
            }
        }

        // Customization Bottom Sheet
        if (showCustomizeSheet) {
            CustomizeSheet(
                preferences = preferences,
                sheetState = sheetState,
                onDismiss = { showCustomizeSheet = false },
                onSaved = {
                    girlfriendName = preferences.girlfriendName
                    senderName = preferences.senderName
                    letterBody = preferences.letterBody
                }
            )
        }
    }
}
