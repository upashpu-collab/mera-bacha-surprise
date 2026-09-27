package com.example

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CardBorderPink
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.PetalPink
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RomanticGold
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EnvelopeLetterSection(
    modifier: Modifier = Modifier,
    letterMessage: String,
    girlfriendName: String,
    senderName: String,
    onContinue: () -> Unit
) {
    var isOpened by remember { mutableStateOf(false) }
    var displayedCharsCount by remember { mutableIntStateOf(0) }
    var isTypingComplete by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val flapRotation = remember { Animatable(0f) }
    val letterSlide = remember { Animatable(0f) }

    // Pulsing seal prompt animation
    val pulseTransition = rememberInfiniteTransition(label = "pulse_seal")
    val sealScale by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "seal_scale"
    )

    fun openEnvelope() {
        if (isOpened) return
        isOpened = true
        scope.launch {
            // Flap opens backwards
            flapRotation.animateTo(180f, animationSpec = tween(600, easing = FastOutSlowInEasing))
            // Letter slides up and out
            letterSlide.animateTo(1f, animationSpec = tween(700, easing = FastOutSlowInEasing))
            // Typewriter effect
            displayedCharsCount = 0
            isTypingComplete = false
            for (i in 1..letterMessage.length) {
                displayedCharsCount = i
                // slightly speed up longer texts
                delay(if (letterMessage[i - 1] == '\n') 120 else 24)
            }
            isTypingComplete = true
        }
    }

    fun retype() {
        scope.launch {
            displayedCharsCount = 0
            isTypingComplete = false
            for (i in 1..letterMessage.length) {
                displayedCharsCount = i
                delay(if (letterMessage[i - 1] == '\n') 100 else 20)
            }
            isTypingComplete = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Section Header
        Text(
            text = "A Letter From My Heart 💌",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RoseDark
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (!isOpened) BirthdayConfig.LETTER_ENVELOPE_PROMPT else "For my one and only $girlfriendName ❤️",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondaryDark,
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Envelope & Letter Visual Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            if (!isOpened) {
                // CLOSED ENVELOPE
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .height(220.dp)
                        .testTag("envelope_card")
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            openEnvelope()
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F3)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Envelope Fold Texture Canvas
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Top flap triangle
                            val topFlap = Path().apply {
                                moveTo(0f, 0f)
                                lineTo(w / 2f, h * 0.55f)
                                lineTo(w, 0f)
                                close()
                            }
                            drawPath(
                                path = topFlap,
                                color = Color(0xFFFFDDE4)
                            )

                            // Bottom flap triangle
                            val bottomFlap = Path().apply {
                                moveTo(0f, h)
                                lineTo(w / 2f, h * 0.45f)
                                lineTo(w, h)
                                close()
                            }
                            drawPath(
                                path = bottomFlap,
                                color = Color(0xFFFFCAD4)
                            )
                        }

                        // Centered Wax Seal with Heart
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .scale(sealScale)
                                .size(64.dp)
                                .shadow(8.dp, CircleShape)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(Color(0xFFFF5252), Color(0xFFB71C1C))
                                    )
                                )
                                .border(2.dp, RomanticGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Wax Seal Heart",
                                tint = PureWhite,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Prompt indicator at bottom
                        Text(
                            text = "✨ Tap to Open ✨",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = RoseDark
                            )
                        )
                    }
                }
            } else {
                // OPENED LETTER (Parchment Paper with Typewriter effect)
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.96f)
                        .wrapContentHeight()
                        .shadow(12.dp, RoundedCornerShape(20.dp))
                        .border(1.5.dp, CardBorderPink, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDFC))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        // Decorative header ribbon
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Heart",
                                tint = RosePrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Dear $girlfriendName,",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = RoseDark,
                                    fontStyle = FontStyle.Italic
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Heart",
                                tint = RosePrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Typewriter Message Body
                        val currentText = letterMessage.take(displayedCharsCount)
                        Text(
                            text = currentText + if (!isTypingComplete) " ✍️|" else "",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = TextPrimaryDark,
                                lineHeight = 28.sp,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Sign-off
                        if (isTypingComplete) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "With all my love & heart,",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontStyle = FontStyle.Italic,
                                        color = TextSecondaryDark
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$senderName ❤️",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = RosePrimary
                                    )
                                )
                            }
                        }

                        // Retype option
                        if (isTypingComplete) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                OutlinedButton(
                                    onClick = { retype() },
                                    modifier = Modifier.testTag("retype_letter_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Read Again",
                                        modifier = Modifier.size(16.dp),
                                        tint = RosePrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Read Letter Again 💌",
                                        color = RosePrimary,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        AnimatedVisibility(
            visible = isOpened,
            enter = fadeIn() + scaleIn()
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(52.dp)
                    .testTag("continue_to_music_button"),
                colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                shape = RoundedCornerShape(26.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = "Play Our Special Music 🎵 →",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                )
            }
        }
    }
}
