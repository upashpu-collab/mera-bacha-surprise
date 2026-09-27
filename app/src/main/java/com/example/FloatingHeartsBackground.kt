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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.sin
import kotlin.random.Random

data class AmbientHeart(
    val xRatio: Float,
    val speed: Float,
    val size: Float,
    val alpha: Float,
    val color: Color,
    val swayFreq: Float,
    val phase: Float
)

@Composable
fun FloatingHeartsBackground(
    modifier: Modifier = Modifier,
    heartCount: Int = 18
) {
    val heartColors = remember {
        listOf(
            Color(0xFFFF8DA1),
            Color(0xFFFFC1CC),
            Color(0xFFFF4081),
            Color(0xFFFFB3BA),
            Color(0xFFFF6F91)
        )
    }

    val ambientHearts = remember {
        List(heartCount) {
            AmbientHeart(
                xRatio = Random.nextFloat(),
                speed = Random.nextFloat() * 0.4f + 0.6f,
                size = Random.nextFloat() * 14f + 10f,
                alpha = Random.nextFloat() * 0.35f + 0.15f,
                color = heartColors.random(),
                swayFreq = Random.nextFloat() * 3f + 2f,
                phase = Random.nextFloat() * 6.28f
            )
        }
    }

    val transition = rememberInfiniteTransition(label = "ambient_hearts")
    val progress = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ambient_progress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        ambientHearts.forEach { hrt ->
            val t = (progress.value * hrt.speed) % 1f
            val y = (1.1f - t * 1.25f) * h
            val x = (hrt.xRatio * w) + sin(progress.value * hrt.swayFreq * 6.28f + hrt.phase) * 18f
            val s = hrt.size

            val path = Path().apply {
                moveTo(x, y)
                cubicTo(x - s / 2, y - s / 2, x - s, y + s / 3, x, y + s)
                cubicTo(x + s, y + s / 3, x + s / 2, y - s / 2, x, y)
                close()
            }

            rotate(degrees = sin(progress.value * 3f + hrt.phase) * 15f, pivot = Offset(x, y)) {
                drawPath(path = path, color = hrt.color.copy(alpha = hrt.alpha))
            }
        }
    }
}
