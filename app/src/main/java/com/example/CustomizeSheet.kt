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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.CardBorderPink
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.PetalPink
import com.example.ui.theme.PureWhite
import com.example.ui.theme.RoseDark
import com.example.ui.theme.RosePrimary
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeSheet(
    preferences: BirthdayPreferences,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSaved: () -> Unit
) {
    val context = LocalContext.current

    var girlfriendName by remember { mutableStateOf(preferences.girlfriendName) }
    var senderName by remember { mutableStateOf(preferences.senderName) }
    var letterBody by remember { mutableStateOf(preferences.letterBody) }

    var selectedPhotoSlot by remember { mutableIntStateOf(-1) }
    // Trigger to re-render photo items
    var photoVersion by remember { mutableIntStateOf(0) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null && selectedPhotoSlot in 0..5) {
            preferences.setPhotoUri(selectedPhotoSlot, uri.toString())
            photoVersion++
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = PureWhite,
        modifier = Modifier.testTag("customize_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Personalize Surprise 💖",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = RoseDark
                    )
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Text(
                text = "Customize her name, your love letter, and photos directly here!",
                style = MaterialTheme.typography.bodySmall.copy(color = TextSecondaryDark)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Girlfriend's Name
            OutlinedTextField(
                value = girlfriendName,
                onValueChange = { girlfriendName = it },
                label = { Text("Girlfriend's Name / Nickname ❤️") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("girlfriend_name_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RosePrimary,
                    focusedLabelColor = RosePrimary
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Sender's Name
            OutlinedTextField(
                value = senderName,
                onValueChange = { senderName = it },
                label = { Text("Your Name (Sender) 💌") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sender_name_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RosePrimary,
                    focusedLabelColor = RosePrimary
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Love Letter Body
            OutlinedTextField(
                value = letterBody,
                onValueChange = { letterBody = it },
                label = { Text("Love Letter Message ✍️") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .testTag("letter_body_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = RosePrimary,
                    focusedLabelColor = RosePrimary
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 6 Photo Memory Slots
            Text(
                text = "6 Photo Memories (Tap slot to choose photo)",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = RoseDark
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Grid of 6 slots (2 rows of 3)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (row in 0..1) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        for (col in 0..2) {
                            val slot = row * 3 + col
                            val uriString = preferences.getPhotoUri(slot)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(CreamBackground)
                                    .border(1.dp, CardBorderPink, RoundedCornerShape(12.dp))
                                    .clickable {
                                        selectedPhotoSlot = slot
                                        photoPicker.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (uriString != null) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data(Uri.parse(uriString))
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Photo $slot",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    // Remove photo badge
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(4.dp)
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xCC000000))
                                            .clickable {
                                                preferences.setPhotoUri(slot, null)
                                                photoVersion++
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = PureWhite,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                } else {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AddPhotoAlternate,
                                            contentDescription = "Add",
                                            tint = PetalPink,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Slot ${slot + 1}",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = TextSecondaryDark,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save & Reset Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        preferences.resetToDefaults()
                        girlfriendName = BirthdayConfig.GIRLFRIEND_NAME
                        senderName = BirthdayConfig.YOUR_NAME
                        letterBody = BirthdayConfig.LETTER_BODY
                        photoVersion++
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reset", fontSize = 13.sp)
                }

                Button(
                    onClick = {
                        preferences.girlfriendName = girlfriendName
                        preferences.senderName = senderName
                        preferences.letterBody = letterBody
                        onSaved()
                        onDismiss()
                    },
                    modifier = Modifier
                        .weight(2f)
                        .testTag("save_customization_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = RosePrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = PureWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save Changes ✨", color = PureWhite, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
