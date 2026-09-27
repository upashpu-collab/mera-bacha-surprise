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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RomanticGold
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.SoftPink
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark

@Composable
fun FinalSurpriseScreen(
    modifier: Modifier = Modifier,
    girlfriendName: String,
    senderName: String,
    onReplay: () -> Unit,
    onOpenCustomize: () -> Unit
) {
    var isFinalRevealed by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "final_glow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "final_pulse"
    )

    val neonGlow by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "neon_glow"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Continuous Confetti celebration when revealed
        if (isFinalRevealed) {
            ConfettiEffect(particleCount = 100, continuous = true)
            BalloonsEffect(balloonCount = 8)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isFinalRevealed) {
                // BEFORE REVEAL: Suspense teaser
                Box(
                    modifier = Modifier
                        .scale(pulseScale)
                        .size(120.dp)
                        .shadow(16.dp, CircleShape)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(RomanticGold, RosePrimary)
                            )
                        )
                        .border(3.dp, PureWhite, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Magic Star",
                        tint = PureWhite,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = BirthdayConfig.FINAL_PRE_TITLE,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = RoseDark
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Before this day moves on, there is one last promise waiting for you...",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TextSecondaryDark,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = { isFinalRevealed = true },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(58.dp)
                        .testTag("reveal_final_surprise_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                    shape = RoundedCornerShape(29.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heart",
                        tint = PureWhite,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = BirthdayConfig.FINAL_REVEAL_BUTTON,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    )
                }
            } else {
                // AFTER REVEAL: Grand Celebration Finale
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(600)) + scaleIn(tween(600, easing = FastOutSlowInEasing))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Glowing Neon Heart
                        Box(
                            modifier = Modifier
                                .scale(pulseScale)
                                .size(130.dp)
                                .shadow(neonGlow.dp, CircleShape)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(Color(0xFFFF1744), Color(0xFFD50000))
                                    )
                                )
                                .border(3.dp, RomanticGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Pulsing Love Heart",
                                tint = PureWhite,
                                modifier = Modifier.size(76.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(14.dp, RoundedCornerShape(28.dp))
                                .border(2.dp, CardBorderPink, RoundedCornerShape(28.dp)),
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite.copy(alpha = 0.97f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 28.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = RomanticGold,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "FOREVER & ALWAYS",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = RosePrimary,
                                            fontWeight = FontWeight.ExtraBold,
                                            letterSpacing = 1.6.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
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
                                        lineHeight = 36.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = BirthdayConfig.FINAL_MESSAGE,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = TextPrimaryDark,
                                        lineHeight = 26.sp,
                                        fontSize = 16.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(18.dp))

                                Text(
                                    text = "— $senderName ❤️",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = RosePrimary,
                                        fontStyle = FontStyle.Italic
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        // Replay Surprise Button
                        Button(
                            onClick = onReplay,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(56.dp)
                                .testTag("replay_surprise_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                            shape = RoundedCornerShape(28.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Replay",
                                tint = PureWhite,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = BirthdayConfig.FINAL_REPLAY_BUTTON,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Discreet in-app customize button
                        OutlinedButton(
                            onClick = onOpenCustomize,
                            modifier = Modifier.testTag("open_customize_sheet_button")
                        ) {
                            Text(
                                text = "✏️ Personalize / Edit Surprise",
                                color = RoseDark,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
