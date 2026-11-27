package com.example.vetcare.view.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
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
import com.example.vetcare.model.data.VetDoctor
import com.example.vetcare.utils.IntentUtils.openDialer

//
//@Composable
//fun MessageScreen(
//    vets: List<VetDoctor>,
//    modifier: Modifier = Modifier,
//    onDoctorClick: (VetDoctor) -> Unit = {}
//) {
//    val context = LocalContext.current
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .background(ScreenBgColor)
//            .padding(horizontal = 20.dp, vertical = 18.dp)
//    ) {
//        Text(
//            text = "Messages & Inquiries",
//            color = DeepNavy,
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = "Quickly contact veterinary clinics directly",
//            color = SubtextGray,
//            fontSize = 13.sp
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//            items(vets, key = { it.id }) { vet ->
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { onDoctorClick(vet) },
//                    shape = RoundedCornerShape(14.dp),
//                    color = Color.White,
//                    border = BorderStroke(1.dp, BorderGray)
//                ) {
//                    Row(
//                        modifier = Modifier.padding(14.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Image(
//                            painter = painterResource(id = vet.imageRes),
//                            contentDescription = vet.name,
//                            modifier = Modifier
//                                .size(54.dp)
//                                .clip(CircleShape)
//                                .background(vet.avatarBg),
//                            contentScale = ContentScale.Crop
//                        )
//                        Spacer(modifier = Modifier.width(12.dp))
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text(
//                                text = vet.name,
//                                color = DeepNavy,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 15.sp
//                            )
//                            Text(
//                                text = vet.specialty,
//                                color = SubtextGray,
//                                fontSize = 12.sp
//                            )
//                        }
//                        IconButton(
//                            onClick = { IntentUtils.openDialer(context, vet.phone) },
//                            modifier = Modifier
//                                .size(38.dp)
//                                .background(Color(0xFFE3F6FB), CircleShape)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Call,
//                                contentDescription = "Call ${vet.name}",
//                                tint = PrimaryTeal,
//                                modifier = Modifier.size(18.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}


@Composable
fun MessageScreen(
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onDoctorClick: (FirestoreClinicItem) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBgColor)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Text(
            text = "Messages & Inquiries",
            color = DeepNavy,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Quickly contact veterinary clinics directly",
            color = SubtextGray,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(uiState.firestoreClinics) { vet ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDoctorClick(FirestoreClinicItem()) },
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, BorderGray)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(vet.avatarBg),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = vet.imageUrl.ifBlank { vet.fallbackImageRes },
                                contentDescription = vet.name,
                                placeholder = painterResource(id = vet.fallbackImageRes),
                                error = painterResource(id = vet.fallbackImageRes),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = vet.name,
                                color = DeepNavy,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = vet.specialty,
                                color = SubtextGray,
                                fontSize = 12.sp
                            )
                        }
                        IconButton(
                            onClick = { openDialer(context, vet.phone) },
                            modifier = Modifier
                                .size(38.dp)
                                .background(Color(0xFFE3F6FB), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Call ${vet.name}",
                                tint = PrimaryTeal,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}