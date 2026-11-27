package com.example.vetcare.view.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vetcare.model.data.ServiceCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidesServices(
    modifier: Modifier = Modifier,
    servicesList: List<ServiceCategory> ,
    onBackClick: () -> Unit = {},
    onServiceClick: (ServiceCategory) -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFFAFAFC),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Services",
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(servicesList) { service ->
                ServiceGridCard(
                    service = service,
                    onClick = { onServiceClick(service) }
                )
            }
        }
    }
}

@Composable
private fun ServiceGridCard(
    service: ServiceCategory,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE6EAF0)),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(68.dp),
                shape = CircleShape,
                color = Color(0xFFF1F6F8)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = service.icon,
                        contentDescription = service.title,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = service.title,
                color = Color(0xFF213A63),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}