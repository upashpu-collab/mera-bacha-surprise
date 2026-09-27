package com.example

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.sin
import kotlin.random.Random

data class BalloonData(
    val xFraction: Float,
    val speed: Float,
    val sizeRadius: Float,
    val baseColor: Color,
    val highlightColor: Color,
    val swayPhase: Float,
    val stringLength: Float
)

@Composable
fun BalloonsEffect(
    modifier: Modifier = Modifier,
    balloonCount: Int = 10
) {
    val balloons = remember {
        val colors = listOf(
            Pair(Color(0xFFE53935), Color(0xFFFF8A80)), // Crimson & Soft red
            Pair(Color(0xFFFF4081), Color(0xFFFF80AB)), // Romantic Pink
            Pair(Color(0xFFBA68C8), Color(0xFFE1BEE7)), // Soft lavender
            Pair(Color(0xFFFFB74D), Color(0xFFFFE0B2)), // Warm peach
            Pair(Color(0xFFFF8A65), Color(0xFFFFCCBC)), // Rose coral
            Pair(Color(0xFFF48FB1), Color(0xFFFCE4EC))  // Blush pink
        )
        List(balloonCount) { i ->
            val colorPair = colors[i % colors.size]
            BalloonData(
                xFraction = 0.08f + (i * 0.85f / balloonCount) + (Random.nextFloat() * 0.06f - 0.03f),
                speed = Random.nextFloat() * 0.35f + 0.65f,
                sizeRadius = Random.nextFloat() * 12f + 32f,
                baseColor = colorPair.first,
                highlightColor = colorPair.second,
                swayPhase = Random.nextFloat() * 6.28f,
                stringLength = Random.nextFloat() * 40f + 65f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "balloons")
    val animProgress = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "balloon_movement"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        balloons.forEach { b ->
            val t = (animProgress.value * b.speed) % 1f
            // Moves from below the screen (1.2 * height) to above the screen (-0.2 * height)
            val currentY = (1.15f - t * 1.35f) * height
            // Gentle sinusoidal sway
            val swayX = sin(animProgress.value * 5f + b.swayPhase) * 20f
            val currentX = (b.xFraction * width) + swayX

            val r = b.sizeRadius

            // 1. Draw Balloon String (curved wavy thread)
            val stringPath = Path().apply {
                moveTo(currentX, currentY + r * 1.1f)
                cubicTo(
                    currentX + 10f * sin(animProgress.value * 4f),
                    currentY + r * 1.1f + b.stringLength * 0.33f,
                    currentX - 10f * sin(animProgress.value * 4f),
                    currentY + r * 1.1f + b.stringLength * 0.66f,
                    currentX,
                    currentY + r * 1.1f + b.stringLength
                )
            }
            drawPath(
                path = stringPath,
                color = Color(0x889E9E9E),
                style = Stroke(width = 2f)
            )

            // 2. Draw Balloon Tie / Knot
            val knotPath = Path().apply {
                moveTo(currentX - 4f, currentY + r * 1.12f)
                lineTo(currentX + 4f, currentY + r * 1.12f)
                lineTo(currentX, currentY + r * 1.05f)
                close()
            }
            drawPath(path = knotPath, color = b.baseColor)

            // 3. Draw Balloon Body with 3D Radial-like Gradient
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(b.highlightColor, b.baseColor),
                    center = Offset(currentX - r * 0.3f, currentY - r * 0.3f),
                    radius = r * 1.4f
                ),
                topLeft = Offset(currentX - r * 0.85f, currentY - r * 1.1f),
                size = Size(r * 1.7f, r * 2.2f)
            )

            // 4. White shine reflection on top-left of balloon
            drawOval(
                color = Color.White.copy(alpha = 0.55f),
                topLeft = Offset(currentX - r * 0.55f, currentY - r * 0.85f),
                size = Size(r * 0.45f, r * 0.8f)
            )
        }
    }
}
