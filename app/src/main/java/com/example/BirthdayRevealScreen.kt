package com.example

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CardBorderPink
import com.example.ui.theme.PetalPink
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RomanticGold
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.SoftPink
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark
import kotlinx.coroutines.delay

@Composable
fun BirthdayRevealScreen(
    modifier: Modifier = Modifier,
    girlfriendName: String,
    onContinue: () -> Unit
) {
    var isRevealed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(150)
        isRevealed = true
    }

    val infiniteTransition = rememberInfiniteTransition(label = "reveal_glow")
    val heartScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_pulse"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Floating Balloons Layer
        BalloonsEffect(balloonCount = 12)

        // Confetti Burst Layer
        ConfettiEffect(particleCount = 85, continuous = true)

        // Center Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isRevealed,
                enter = fadeIn(tween(700)) + scaleIn(tween(700, easing = FastOutSlowInEasing))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Beating Birthday Heart & Cake Emblem
                    Box(
                        modifier = Modifier
                            .scale(heartScale)
                            .size(110.dp)
                            .shadow(16.dp, CircleShape)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(RosePrimary, Color(0xFFD81B60))
                                )
                            )
                            .border(3.dp, PureWhite, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cake,
                            contentDescription = "Birthday Cake",
                            tint = PureWhite,
                            modifier = Modifier.size(56.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Birthday Hero Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, CardBorderPink, RoundedCornerShape(28.dp)),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite.copy(alpha = 0.96f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 22.dp, vertical = 26.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Sparkle",
                                    tint = RomanticGold,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "IT'S YOUR SPECIAL DAY!",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = RosePrimary,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.5.sp
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Sparkle",
                                    tint = RomanticGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Happy Birthday,\n$girlfriendName ❤️",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = RoseDark,
                                    lineHeight = 36.sp,
                                    fontSize = 26.sp
                                ),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = BirthdayConfig.REVEAL_SUBTITLE,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = TextSecondaryDark,
                                    lineHeight = 24.sp,
                                    fontSize = 15.sp,
                                    fontStyle = FontStyle.Italic
                                ),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🎈", fontSize = 22.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "🎉", fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "🎂", fontSize = 26.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "🥂", fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "💖", fontSize = 22.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(36.dp))

                    // "Continue →" Button
                    Button(
                        onClick = onContinue,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(56.dp)
                            .testTag("continue_to_memories_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                        shape = RoundedCornerShape(28.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = BirthdayConfig.REVEAL_BUTTON,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PureWhite,
                                fontSize = 17.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
