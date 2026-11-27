package com.example.vetcare.view.Screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vetcare.ViewModel.HomeViewModel
import com.example.vetcare.model.data.FirestoreClinicItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDoctorClinicScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val uiState by homeViewModel.uiState.collectAsState()

    // Form States covering all FirestoreClinicItem parameters
    var id by remember { mutableStateOf("vet_1") }
    var placeId by remember { mutableStateOf("vet_1") }
    var name by remember { mutableStateOf("Dr. Michael Grossett") }
    var specialty by remember { mutableStateOf("Veterinary Neurosurgeon") }
    var address by remember { mutableStateOf("DMC Hospital, Sector 14, Gurugram") }
    var phone by remember { mutableStateOf("9876543210") }
    var rating by remember { mutableStateOf("4.8") }
    var distance by remember { mutableStateOf("1.2 KM Away") }
    var latitude by remember { mutableStateOf("28.4744") }
    var longitude by remember { mutableStateOf("77.0405") }
    var direction by remember { mutableStateOf("DMC Hospital, Sector 14, Gurugram") }
    var avatarBgHex by remember { mutableStateOf("#DDE7F0") }
    var imageUrl by remember { mutableStateOf("https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=400") }
    var experience by remember { mutableStateOf("8 Years Experience") }
    var about by remember {
        mutableStateOf("Senior veterinary neurosurgeon specializing in spinal surgery, neurological disorders, and critical care for dogs and cats.")
    }
    var openDays by remember { mutableStateOf("Monday - Saturday") }
    var openHours by remember { mutableStateOf("09:30 AM - 07:30 PM") }

    val colorPresets = listOf("#DDE7F0", "#E8E2D9", "#E3F6FB", "#F5D0D6", "#E2F0D9")

    // Show Toast feedback when Firestore save succeeds or fails
    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            if (msg.contains("saved", ignoreCase = true)) {
//                onBackClick()
            }
        }
    }

    Scaffold(
        containerColor = ScreenBgColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Doctor / Clinic",
                        color = DeepNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = DeepNavy
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Live Avatar & Image URL Preview Card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White,
                border = BorderStroke(1.dp, BorderGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val parsedBg = remember(avatarBgHex) {
                        try {
                            Color(android.graphics.Color.parseColor(avatarBgHex))
                        } catch (e: Exception) {
                            Color(0xFFDDE7F0)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(parsedBg),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Avatar Background Color",
                            color = DeepNavy,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            colorPresets.forEach { hex ->
                                val swatchColor = Color(android.graphics.Color.parseColor(hex))
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(swatchColor)
                                        .border(
                                            width = if (avatarBgHex.equals(hex, ignoreCase = true)) 2.dp else 1.dp,
                                            color = if (avatarBgHex.equals(hex, ignoreCase = true)) PrimaryTeal else BorderGray,
                                            shape = CircleShape
                                        )
                                        .clickable { avatarBgHex = hex }
                                )
                            }
                        }
                    }
                }
            }

            // Section 1: Identifiers
            SectionTitle("Identifiers")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClinicFormField(
                    value = id,
                    onValueChange = { id = it },
                    label = "ID",
                    modifier = Modifier.weight(1f)
                )
                ClinicFormField(
                    value = placeId,
                    onValueChange = { placeId = it },
                    label = "Place ID",
                    modifier = Modifier.weight(1f)
                )
            }

            // Section 2: Doctor / Clinic Details
            SectionTitle("Doctor & Clinic Info")
            ClinicFormField(value = name, onValueChange = { name = it }, label = "Doctor / Clinic Name")
            ClinicFormField(value = specialty, onValueChange = { specialty = it }, label = "Specialty")
            ClinicFormField(value = experience, onValueChange = { experience = it }, label = "Experience")
            ClinicFormField(
                value = phone,
                onValueChange = { phone = it },
                label = "Phone Number",
                keyboardType = KeyboardType.Phone
            )
            ClinicFormField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = "Image URL"
            )
            ClinicFormField(
                value = avatarBgHex,
                onValueChange = { avatarBgHex = it },
                label = "Avatar Background Hex (e.g. #DDE7F0)"
            )

            // Section 3: Rating & Distance
            SectionTitle("Metrics")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClinicFormField(
                    value = rating,
                    onValueChange = { rating = it },
                    label = "Rating",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                ClinicFormField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = "Distance",
                    modifier = Modifier.weight(1f)
                )
            }

            // Section 4: Location & Coordinates
            SectionTitle("Location & Directions")
            ClinicFormField(value = address, onValueChange = { address = it }, label = "Address")
            ClinicFormField(value = direction, onValueChange = { direction = it }, label = "Directions Query")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClinicFormField(
                    value = latitude,
                    onValueChange = { latitude = it },
                    label = "Latitude",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                ClinicFormField(
                    value = longitude,
                    onValueChange = { longitude = it },
                    label = "Longitude",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
            }

            // Section 5: Schedule & Bio
            SectionTitle("Schedule & About")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ClinicFormField(
                    value = openDays,
                    onValueChange = { openDays = it },
                    label = "Open Days",
                    modifier = Modifier.weight(1f)
                )
                ClinicFormField(
                    value = openHours,
                    onValueChange = { openHours = it },
                    label = "Open Hours",
                    modifier = Modifier.weight(1f)
                )
            }
            ClinicFormField(
                value = about,
                onValueChange = { about = it },
                label = "About",
                singleLine = false,
                minLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Submit Button -> Saves all parameters to Firebase Firestore
            Button(
                enabled = !uiState.isSavingClinic,
                onClick = {
                    if (name.isBlank() || address.isBlank()) {
                        Toast.makeText(context, "Name and Address are required", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val clinicItem = FirestoreClinicItem(
                        id = id.trim(),
                        placeId = placeId.trim().ifBlank { id.trim() },
                        name = name.trim(),
                        specialty = specialty.trim(),
                        address = address.trim(),
                        phone = phone.trim(),
                        rating = rating.trim(),
                        distance = distance.trim(),
                        latitude = latitude.toDoubleOrNull() ?: 0.0,
                        longitude = longitude.toDoubleOrNull() ?: 0.0,
                        createdAt = System.currentTimeMillis(),
                        direction = direction.trim().ifBlank { address.trim() },
                        avatarBgHex = avatarBgHex.trim(),
                        imageUrl = imageUrl.trim(),
                        experience = experience.trim(),
                        about = about.trim(),
                        openDays = openDays.trim(),
                        openHours = openHours.trim()
                    )

                    homeViewModel.saveClinicToFirestore(clinicItem)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryTeal),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isSavingClinic) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Text(
                        text = "Submit to Firebase",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = PrimaryTeal,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
private fun ClinicFormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 13.sp) },
        singleLine = singleLine,
        minLines = minLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryTeal,
            unfocusedBorderColor = BorderGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        modifier = modifier.fillMaxWidth()
    )
}