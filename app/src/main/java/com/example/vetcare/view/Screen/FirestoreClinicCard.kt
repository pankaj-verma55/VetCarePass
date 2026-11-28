package com.example.vetcare.view.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vetcare.ViewModel.HomeViewModel
import com.example.vetcare.model.data.FirestoreClinicItem
import com.example.vetcare.utils.IntentUtils

@Composable
fun FirestoreClinicCard(
    clinic: FirestoreClinicItem,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, BorderGray)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .clickable { onCardClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(clinic.avatarBg),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = clinic.imageUrl.ifBlank { clinic.fallbackImageRes },
                    contentDescription = clinic.name,
                    placeholder = painterResource(id = clinic.fallbackImageRes),
                    error = painterResource(id = clinic.fallbackImageRes),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = clinic.name,
                    color = DeepNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = clinic.address.ifBlank { clinic.specialty },
                    color = SubtextGray,
                    fontSize = 13.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoBadge(
                        icon = Icons.Default.Star,
                        iconTint = RatingGold,
                        text = clinic.rating.ifBlank { "4.5" },
                        textColor = RatingGold
                    )
                    if (clinic.distance.isNotBlank()) {
                        InfoBadge(
                            icon = Icons.Default.LocationOn,
                            iconTint = DeepNavy,
                            text = clinic.distance,
                            textColor = SubtextGray
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (clinic.phone.isNotBlank()) {
                    IconButton(
                        onClick = { IntentUtils.openDialer(context, clinic.phone) },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFE3F6FB), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call ${clinic.name}",
                            tint = PrimaryTeal,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                IconButton(
                    onClick = {
                        val destination = clinic.address.ifBlank { clinic.name }
                        IntentUtils.openGoogleMapsDirections(context, destination)
                    },
                    modifier = Modifier.padding(top = 5.dp)
                        .size(36.dp)
                        .background(Color(0xFFE8EEF5), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Directions,
                        contentDescription = "Directions to ${clinic.name}",
                        tint = DeepNavy,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}