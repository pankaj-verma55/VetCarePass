package com.example.vetcare.view.Screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vetcare.ViewModel.HomeViewModel
import com.example.vetcare.model.data.FirestoreClinicItem
import com.example.vetcare.model.data.VetDoctor
import kotlin.collections.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeterinaryNearYou(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    vets: List<VetDoctor> = emptyList(),
    onBackClick: () -> Unit = {},
    onDoctorClick: (VetDoctor) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredClinics: List<FirestoreClinicItem> = remember(searchQuery, uiState.firestoreClinics) {
        if (searchQuery.isBlank()) {
            uiState.firestoreClinics
        } else {
            uiState.firestoreClinics.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.specialty.contains(searchQuery, ignoreCase = true) ||
                        it.address.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFFAFAFC),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Veterinary Near You",
                        color = Color(0xFF213A63),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF213A63)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFAFAFC)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                placeholder = {
                    Text("Search doctor or specialty...", fontSize = 14.sp)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF7B8794)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search",
                                tint = Color(0xFF7B8794)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF24A8C2),
                    unfocusedBorderColor = Color(0xFFE6EAF0)
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = filteredClinics,
                    key = { it.id }
                ) { clinic ->
                    // 3. Pass the single `clinic` item and map it to VetDoctor on click
                    FirestoreClinicCard(
                        clinic = clinic,
                        onCardClick = {
                            onDoctorClick(
                                VetDoctor(
                                    name = clinic.name,
                                    specialty = clinic.specialty,
                                    rating = clinic.rating,
                                    distance = clinic.distance,
                                    avatarBg = clinic.avatarBg,
                                    imageRes = clinic.fallbackImageRes,
                                    phone = clinic.phone,
                                    direction = clinic.direction.ifBlank { clinic.address },
                                    experience = clinic.experience,
                                    about = clinic.about,
                                    openDays = clinic.openDays,
                                    openHours = clinic.openHours
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}