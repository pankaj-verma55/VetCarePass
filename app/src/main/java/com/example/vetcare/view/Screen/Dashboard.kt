package com.example.vetcare.view.Screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.vetcare.R
import com.example.vetcare.ui.icon.PetCareIcons

// Color Palette extracted from the design
private val PrimaryTeal = Color(0xFF24A8C2)
private val DeepNavy = Color(0xFF213A63)
private val HeaderBgColor = Color(0xFFF1F6F8)
private val ScreenBgColor = Color(0xFFFAFAFC)
private val RatingGold = Color(0xFFF6C23E)
private val BadgeGrayBg = Color(0xFFF4F6F9)
private val BorderGray = Color(0xFFE6EAF0)
private val SubtextGray = Color(0xFF7B8794)

data class ServiceCategory(
    val title: String,
    val icon: ImageVector
)

data class VetDoctor(
    val name: String,
    val specialty: String,
    val rating: String,
    val distance: String,
    val avatarBg: Color,
    @DrawableRes val imageRes: Int,
    val phone: String,
    val direction: String,
    val experience: String = "3 Year Experience",
    val about: String = "Experienced veterinary specialist dedicated to preventive healthcare, diagnostics, and compassionate treatment for dogs, cats, and small companion pets.",
    val openDays: String = "Monday - Saturday",
    val openHours: String = "09:30 AM - 07:30 PM"
)
val services = listOf(
    ServiceCategory("Veterinary", PetCareIcons.VeterinaryBadge),
    ServiceCategory("Food", PetCareIcons.FoodBowl),
    ServiceCategory("Pharmacy", PetCareIcons.PharmacyCross),
    ServiceCategory("Boarding", PetCareIcons.PetBoarding),
    ServiceCategory("Grooming", Icons.Default.Pets)
)

val vets = listOf(
    VetDoctor(
        name = "Dr. Michael Grossett",
        specialty = "Veterinary Neurosurgeon",
        rating = "4.1",
        distance = "1.2 KM Away",
        avatarBg = Color(0xFFDDE7F0),
        imageRes = R.drawable.img_doctor,
        phone = "9876543210",
        direction = "Apex Hospital, Sirsa"
    ),
    VetDoctor(
        name = "Dr. Taylor Muhs",
        specialty = "Animal Welfare",
        rating = "4.2",
        distance = "2.0 KM Away",
        avatarBg = Color(0xFFE8E2D9),
        imageRes = R.drawable.img_doctor1,
        phone = "9876543210",
        direction = "Apex Hospital, Sirsa"
    ),
    VetDoctor(
        name = "Dr. Pankaj",
        specialty = "Animal Welfare",
        rating = "4.9",
        distance = "4.0 KM Away",
        avatarBg = Color(0xFFDDE7F0),
        imageRes = R.drawable.img_doctor2,
        phone = "9876543210",
        direction = "Appex Hospital, Sirsa"
    ),
    VetDoctor(
        name = "Dr. Amar",
        specialty = "Animal Welfare",
        rating = "4.9",
        distance = "1.0 KM Away",
        avatarBg = Color(0xFFE8E2D9),
        imageRes = R.drawable.img_doctor3,
        phone = "9876543210",
        direction = "Apex Hospital, Sirsa"
    ),
    VetDoctor(
        name = "Dr. Taylor Muhs",
        specialty = "Skin Problem",
        rating = "4.8",
        distance = "2.0 KM Away",
        avatarBg = Color(0xFFDDE7F0),
        imageRes = R.drawable.img_doctor4,
        phone = "9876543210",
        direction = "Apex Hospital, Sirsa"
    ),
    VetDoctor(
        name = "Dr. Taylor",
        specialty = "Animal Welfare",
        rating = "4.2",
        distance = "2.0 KM Away",
        avatarBg = Color(0xFFE8E2D9),
        imageRes = R.drawable.img_doctor5,
        phone = "9876543210",
        direction = "Apex Hospital, Sirsa"
    )
)
@Composable
fun PetCareHomeScreen(
    onSeeAllServicesClick: () -> Unit = {},
    onSeeAllVeterinaryClick: () -> Unit = {},
    onDoctorClick: (VetDoctor) -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }



    Scaffold(
        containerColor = ScreenBgColor,
        bottomBar = {
            PetCareBottomNav(
                selectedIndex = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            // 1. Top Header & Hero Section
            item {
                TopHeroBanner()
            }

            // 2. Services Section
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SectionHeader(title = "Services", onSeeAllClick = onSeeAllServicesClick)
                Spacer(modifier = Modifier.height(14.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(services) { service ->
                        ServiceItem(service)
                    }
                }
            }

//            // 3. Veterinary Near You Section
//            item {
//                Spacer(modifier = Modifier.height(24.dp))
//                SectionHeader(title = "Veterinary Near You", onSeeAllClick = onSeeAllVeterinaryClick)
//                Spacer(modifier = Modifier.height(14.dp))
//            }

            // 3. Veterinary Near You Section (Fixed-height box showing 4 items)
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(
                    title = "Veterinary Near You",
                    onSeeAllClick = onSeeAllVeterinaryClick
                )
                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp) 
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(vets.take(4)) { vet ->
                            VetDoctorCard(vet = vet,
                                onCardClick = { onDoctorClick(vet) })
                        }
                    }
                }
            }
//            items(vets) { vet ->
//                VetDoctorCard(
//                    vet = vet,
//                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
//                )
//            }
        }
    }
}

@Composable
private fun TopHeroBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderBgColor)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        // User Greeting Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Profile Avatar Placeholder (Replace with Coil AsyncImage in production)
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF5D0D6))
                        .border(1.5.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🧑", fontSize = 24.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Hii Pankaj 👋",
                        color = PrimaryTeal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Gurugram, India",
                        color = Color(0xFF1F2933),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            IconButton(onClick = { /* Handle notifications */ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = DeepNavy,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Hero Banner Content
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1.1f)) {
                Text(
                    text = "What Service do\nyou Need?",
                    color = PrimaryTeal,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 21.sp,
                    lineHeight = 28.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = { /* Handle Get Started */ },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Get Started",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // 3D Illustration Placeholder (Replace with PainterResource / Image asset)
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .height(130.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.parent_icon),
                    contentDescription = "parent photo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(290.dp)),
                    contentScale = ContentScale.Fit
                )
//                Text(text = "🧑🏻‍🌾🐕", fontSize = 56.sp)
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = PrimaryTeal,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        Row(
            modifier = Modifier.clickable { onSeeAllClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See All",
                color = DeepNavy,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "See All",
                tint = DeepNavy,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun ServiceItem(service: ServiceCategory) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle category click */ }
    ) {
        Surface(
            modifier = Modifier.size(68.dp),
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(1.dp, BorderGray),
            shadowElevation = 1.dp
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = service.title,
            color = Color(0xFF333E48),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
@Composable
fun VetDoctorCard(vet: VetDoctor, modifier: Modifier = Modifier,
                  onCardClick: () -> Unit = {}) {
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, BorderGray)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).clickable{onCardClick()},
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(vet.avatarBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = vet.imageRes),
                    contentDescription = vet.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vet.name,
                    color = DeepNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = vet.specialty,
                    color = SubtextGray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoBadge(
                        icon = Icons.Default.Star,
                        iconTint = RatingGold,
                        text = vet.rating,
                        textColor = RatingGold
                    )
                    InfoBadge(
                        icon = Icons.Default.LocationOn,
                        iconTint = DeepNavy,
                        text = vet.distance,
                        textColor = SubtextGray
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { openDialer(context, vet.phone) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFE3F6FB), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call ${vet.name}",
                        tint = PrimaryTeal,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = { openGoogleMapsDirections(context, vet.direction) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .size(36.dp)
                        .background(Color(0xFFE8EEF5), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Directions,
                        contentDescription = "Directions to ${vet.name}",
                        tint = DeepNavy,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
//@Composable
//private fun VetDoctorCard(vet: VetDoctor, modifier: Modifier = Modifier) {
//    Surface(
//        modifier = modifier.fillMaxWidth(),
//        shape = RoundedCornerShape(14.dp),
//        color = Color.White,
//        border = BorderStroke(1.dp, BorderGray)
//    ) {
//        Row(
//            modifier = Modifier.padding(12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Vet Image Container (Replace with Coil AsyncImage)
//            Box(
//                modifier = Modifier
//                    .size(86.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(vet.avatarBg),
//                contentAlignment = Alignment.Center
//            ) {
//                if (vet.imageRes != null) {
//                    Image(
//                        painter = painterResource(id = vet.imageRes),
//                        contentDescription = vet.name,
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop
//                    )
//                } else {
//                    Text(text = "🩺🐶", fontSize = 28.sp)
//                }
////                Image(painter = painterResource(vet.imageRes), contentDescription = "doctor image")
////                Text(text = "🩺🐶", fontSize = 28.sp)
//            }
//
//            Spacer(modifier = Modifier.width(14.dp))
//
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = vet.name,
//                    color = DeepNavy,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = vet.specialty,
//                    color = SubtextGray,
//                    fontSize = 13.sp
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//
//                // Rating and Distance Chips
//                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                    InfoBadge(
//                        icon = Icons.Default.Star,
//                        iconTint = RatingGold,
//                        text = vet.rating,
//                        textColor = RatingGold
//                    )
//                    InfoBadge(
//                        icon = Icons.Default.LocationOn,
//                        iconTint = DeepNavy,
//                        text = vet.distance,
//                        textColor = SubtextGray
//                    )
//                }
//            }
//        }
//    }
//}

@Composable
private fun InfoBadge(
    icon: ImageVector,
    iconTint: Color,
    text: String,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .background(BadgeGrayBg, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun PetCareBottomNav(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val navItems = listOf(
        Pair("Home", Icons.Default.Home),
        Pair("Message", Icons.Outlined.ChatBubbleOutline),
        Pair("My Booking", Icons.Outlined.CalendarMonth),
        Pair("Profile", Icons.Outlined.PersonOutline)
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.second,
                        contentDescription = item.first,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.first,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DeepNavy,
                    selectedTextColor = DeepNavy,
                    unselectedIconColor = SubtextGray,
                    unselectedTextColor = SubtextGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

fun openDialer(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:$phoneNumber".toUri()
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No phone app found", Toast.LENGTH_SHORT).show()
    }
}

fun openGoogleMapsDirections(context: Context, destination: String) {
    val encodedDest = Uri.encode(destination)
    val mapsUri = "google.navigation:q=$encodedDest".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, mapsUri).apply {
        setPackage("com.google.android.apps.maps")
    }

    try {
        context.startActivity(mapIntent)
    } catch (e: Exception) {
        val webUri = "https://www.google.com/maps/dir/?api=1&destination=$encodedDest".toUri()
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
}

@Preview(showBackground = true)
@Composable
fun PetCareHomeScreenPreview() {
    PetCareHomeScreen()
}