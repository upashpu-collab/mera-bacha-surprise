package com.example

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random

data class ConfettiParticle(
    val initialX: Float, // 0..1 fraction
    val initialY: Float, // -0.2..0.2 fraction
    val speedY: Float,
    val speedX: Float,
    val rotationSpeed: Float,
    val size: Float,
    val color: Color,
    val shape: ParticleShape,
    val initialAngle: Float
)

enum class ParticleShape {
    Heart, Rectangle, Circle, Star
}

@Composable
fun ConfettiEffect(
    modifier: Modifier = Modifier,
    particleCount: Int = 70,
    continuous: Boolean = false
) {
    val romanticColors = remember {
        listOf(
            Color(0xFFE53935), // Red
            Color(0xFFFF4081), // Pink
            Color(0xFFFF80AB), // Soft pink
            Color(0xFFFFB300), // Gold
            Color(0xFFFFD54F), // Light gold
            Color(0xFFFFFFFF), // White
            Color(0xFFFF8DA1), // Petal pink
            Color(0xFFF06292)  // Rose
        )
    }

    val particles = remember {
        List(particleCount) {
            ConfettiParticle(
                initialX = Random.nextFloat(),
                initialY = if (continuous) Random.nextFloat() * 1.2f - 0.2f else Random.nextFloat() * 0.3f - 0.3f,
                speedY = Random.nextFloat() * 0.5f + 0.5f,
                speedX = (Random.nextFloat() - 0.5f) * 0.3f,
                rotationSpeed = Random.nextFloat() * 360f - 180f,
                size = Random.nextFloat() * 12f + 10f,
                color = romanticColors.random(),
                shape = when (Random.nextInt(4)) {
                    0 -> ParticleShape.Heart
                    1 -> ParticleShape.Star
                    2 -> ParticleShape.Circle
                    else -> ParticleShape.Rectangle
                },
                initialAngle = Random.nextFloat() * 360f
            )
        }
    }

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        if (continuous) {
            while (true) {
                progress.animateTo(
                    targetValue = progress.value + 1f,
                    animationSpec = tween(durationMillis = 3500, easing = LinearEasing)
                )
            }
        } else {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 4000, easing = LinearEasing)
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        particles.forEach { p ->
            val elapsed = progress.value
            val currentY = if (continuous) {
                ((p.initialY + elapsed * p.speedY) % 1.3f) * height
            } else {
                (p.initialY + elapsed * p.speedY * 1.4f) * height
            }

            if (currentY in -50f..(height + 50f)) {
                val currentX = ((p.initialX + elapsed * p.speedX).mod(1.0f)) * width
                val currentAngle = p.initialAngle + elapsed * p.rotationSpeed

                rotate(degrees = currentAngle, pivot = Offset(currentX, currentY)) {
                    when (p.shape) {
                        ParticleShape.Heart -> {
                            val heartPath = Path().apply {
                                val s = p.size
                                moveTo(currentX, currentY)
                                cubicTo(
                                    currentX - s / 2, currentY - s / 2,
                                    currentX - s, currentY + s / 3,
                                    currentX, currentY + s
                                )
                                cubicTo(
                                    currentX + s, currentY + s / 3,
                                    currentX + s / 2, currentY - s / 2,
                                    currentX, currentY
                                )
                                close()
                            }
                            drawPath(path = heartPath, color = p.color)
                        }
                        ParticleShape.Star -> {
                            val s = p.size * 0.7f
                            val starPath = Path().apply {
                                moveTo(currentX, currentY - s)
                                lineTo(currentX + s * 0.3f, currentY - s * 0.3f)
                                lineTo(currentX + s, currentY)
                                lineTo(currentX + s * 0.3f, currentY + s * 0.3f)
                                lineTo(currentX, currentY + s)
                                lineTo(currentX - s * 0.3f, currentY + s * 0.3f)
                                lineTo(currentX - s, currentY)
                                lineTo(currentX - s * 0.3f, currentY - s * 0.3f)
                                close()
                            }
                            drawPath(path = starPath, color = p.color)
                        }
                        ParticleShape.Circle -> {
                            drawCircle(
                                color = p.color,
                                radius = p.size / 2.5f,
                                center = Offset(currentX, currentY)
                            )
                        }
                        ParticleShape.Rectangle -> {
                            drawRect(
                                color = p.color,
                                topLeft = Offset(currentX - p.size / 2, currentY - p.size / 3),
                                size = Size(p.size, p.size * 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}
