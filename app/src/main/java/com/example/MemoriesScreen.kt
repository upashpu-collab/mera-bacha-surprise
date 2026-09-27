package com.example

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.CardBorderPink
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.PetalPink
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RomanticGold
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.SoftPink
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark

@Composable
fun MemoriesScreen(
    modifier: Modifier = Modifier,
    preferences: BirthdayPreferences,
    onContinue: () -> Unit
) {
    val context = LocalContext.current
    var activePickingIndex by remember { mutableIntStateOf(-1) }

    // Android Zero-permission Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null && activePickingIndex in 0..5) {
            preferences.setPhotoUri(activePickingIndex, uri.toString())
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("memories_list"),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Section Header
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heart",
                        tint = RosePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "OUR CHERISHED MEMORIES",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = RosePrimary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Heart",
                        tint = RosePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Moments That Made Me Fall in Love ✨",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = RoseDark
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Tap on any photo card below to replace it with your real picture! 📸",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextSecondaryDark,
                        fontStyle = FontStyle.Italic
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        // 6 Polaroid-Style Memory Cards
        itemsIndexed(BirthdayConfig.DEFAULT_MEMORIES) { index, memory ->
            val photoUriString = preferences.getPhotoUri(index)
            val caption = preferences.getPhotoCaption(index, memory.defaultCaption)
            val slightAngle = remember { if (index % 2 == 0) -1.2f else 1.2f }

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .rotate(slightAngle)
                    .shadow(12.dp, RoundedCornerShape(20.dp))
                    .border(2.dp, CardBorderPink, RoundedCornerShape(20.dp))
                    .testTag("memory_card_$index")
                    .clickable {
                        activePickingIndex = index
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Tape / Washi effect
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(RomanticGold.copy(alpha = 0.5f))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Photo Area (Custom Photo or Artistic Gradient Placeholder)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.2f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = memory.gradientColors.map { Color(it) }
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (photoUriString != null) {
                            // User-picked real image
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(Uri.parse(photoUriString))
                                    .crossfade(true)
                                    .build(),
                                contentDescription = memory.defaultTitle,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            // Artistic romantic placeholder (no copyrighted external images)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = memory.placeholderEmoji,
                                    fontSize = 48.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = memory.defaultTitle,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = PureWhite,
                                        fontWeight = FontWeight.Bold,
                                        shadow = androidx.compose.ui.graphics.Shadow(
                                            color = Color(0x66000000),
                                            blurRadius = 6f
                                        )
                                    ),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = PureWhite.copy(alpha = 0.85f),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AddPhotoAlternate,
                                            contentDescription = "Add Photo",
                                            tint = RosePrimary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Tap to Add Photo 📷",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = RosePrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        // Floating badge with photo number
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(PureWhite.copy(alpha = 0.9f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = RoseDark
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Romantic Caption
                    Text(
                        text = caption,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextPrimaryDark,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Change Photo Small Action Tag
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(CreamBackground)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change photo",
                            tint = RosePrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (photoUriString != null) "Change Photo" else "Upload Memory",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RosePrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }

        // Continue Button at bottom of memories list
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(56.dp)
                    .testTag("continue_to_letter_button"),
                colors = ButtonDefaults.buttonColors(containerColor = RosePrimary),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Open Your Love Letter 💌 →",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        fontSize = 17.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
