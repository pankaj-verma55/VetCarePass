package com.example.vetcare.view.Screen

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vetcare.R
import com.example.vetcare.ViewModel.HomeViewModel
import com.example.vetcare.model.data.FirestoreClinicItem
import com.example.vetcare.model.data.ServiceCategory
import com.example.vetcare.model.data.VetDoctor
import com.example.vetcare.utils.IntentUtils

val PrimaryTeal = Color(0xFF24A8C2)
val DeepNavy = Color(0xFF213A63)
private val HeaderBgColor = Color(0xFFF1F6F8)
val ScreenBgColor = Color(0xFFFAFAFC)
val RatingGold = Color(0xFFF6C23E)
private val BadgeGrayBg = Color(0xFFF4F6F9)
val BorderGray = Color(0xFFE6EAF0)
val SubtextGray = Color(0xFF7B8794)

//val services = listOf(
//    ServiceCategory("Veterinary", PetCareIcons.VeterinaryBadge),
//    ServiceCategory("Food", PetCareIcons.FoodBowl),
//    ServiceCategory("Pharmacy", PetCareIcons.PharmacyCross),
//    ServiceCategory("Boarding", PetCareIcons.PetBoarding),
//    ServiceCategory("Grooming", Icons.Default.Pets)
//)
@Composable
fun DashBoardScreen(
    viewModel: HomeViewModel = viewModel(),
    onSeeAllServicesClick: () -> Unit = {},
    onSeeAllVeterinaryClick: () -> Unit = {},
    onDoctorClick: (VetDoctor) -> Unit = {},
    onAddDoctor: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        val tenDoctorsList = listOf(
            FirestoreClinicItem(
                id = "vet_1",
                placeId = "vet_1",
                name = "Dr. Michael Grossett",
                specialty = "Veterinary Neurosurgeon",
                address = "DMC Hospital, Sector 14, Gurugram",
                phone = "9876543210",
                rating = "4.8",
                distance = "1.2 KM Away",
                latitude = 28.4744,
                longitude = 77.0405,
                direction = "DMC Hospital, Sector 14, Gurugram",
                avatarBgHex = "#DDE7F0",
                imageUrl = "https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=400",
                experience = "8 Years Experience",
                about = "Senior veterinary neurosurgeon specializing in spinal surgery, neurological disorders, and critical care for dogs and cats.",
                openDays = "Monday - Saturday",
                openHours = "09:30 AM - 07:30 PM"
            ),
            FirestoreClinicItem(
                id = "vet_2",
                placeId = "vet_2",
                name = "Dr. Taylor Muhs",
                specialty = "Animal Welfare & General Care",
                address = "Apex Pet Hospital, DLF Phase 4, Gurugram",
                phone = "9811223344",
                rating = "4.6",
                distance = "2.0 KM Away",
                latitude = 28.4652,
                longitude = 77.0815,
                direction = "Apex Pet Hospital, DLF Phase 4, Gurugram",
                avatarBgHex = "#E8E2D9",
                imageUrl = "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=400",
                experience = "5 Years Experience",
                about = "Dedicated to preventive healthcare, routine vaccinations, nutrition counseling, and compassionate treatment for companion pets.",
                openDays = "Monday - Saturday",
                openHours = "10:00 AM - 08:00 PM"
            ),
            FirestoreClinicItem(
                id = "vet_3",
                placeId = "vet_3",
                name = "Dr. Pankaj Sharma",
                specialty = "Small Animal Surgeon",
                address = "New Life Care Vet Clinic, Sushant Lok 1, Gurugram",
                phone = "9911002233",
                rating = "4.9",
                distance = "2.4 KM Away",
                latitude = 28.4595,
                longitude = 77.0724,
                direction = "New Life Care Vet Clinic, Sushant Lok 1, Gurugram",
                avatarBgHex = "#DDE7F0",
                imageUrl = "https://images.unsplash.com/photo-1622253692010-333f2da6031d?w=400",
                experience = "7 Years Experience",
                about = "Experienced soft-tissue and orthopedic veterinary surgeon with modern ultrasound and diagnostic facilities.",
                openDays = "Monday - Sunday",
                openHours = "09:00 AM - 09:00 PM"
            ),
            FirestoreClinicItem(
                id = "vet_4",
                placeId = "vet_4",
                name = "Dr. Amar Verma",
                specialty = "Veterinary Dermatologist",
                address = "Paws & Fur Clinic, Sector 56, Gurugram",
                phone = "9871122334",
                rating = "4.7",
                distance = "1.5 KM Away",
                latitude = 28.4235,
                longitude = 77.1012,
                direction = "Paws & Fur Clinic, Sector 56, Gurugram",
                avatarBgHex = "#E8E2D9",
                imageUrl = "https://images.unsplash.com/photo-1537368910025-700350fe46c7?w=400",
                experience = "6 Years Experience",
                about = "Specialist in pet skin allergies, fungal infections, coat health, and chronic ear conditions.",
                openDays = "Tuesday - Sunday",
                openHours = "10:00 AM - 07:00 PM"
            ),
            FirestoreClinicItem(
                id = "vet_5",
                placeId = "vet_5",
                name = "Dr. Ananya Rao",
                specialty = "Feline & Exotic Pet Specialist",
                address = "Whiskers & Tails Hospital, South City 2, Gurugram",
                phone = "9810987654",
                rating = "4.9",
                distance = "3.1 KM Away",
                latitude = 28.4148,
                longitude = 77.0561,
                direction = "Whiskers & Tails Hospital, South City 2, Gurugram",
                avatarBgHex = "#DDE7F0",
                imageUrl = "https://images.unsplash.com/photo-1594824476967-48c8b964273f?w=400",
                experience = "9 Years Experience",
                about = "Fear-free feline medicine specialist providing low-stress diagnostics, dental care, and wellness plans for cats and birds.",
                openDays = "Monday - Saturday",
                openHours = "09:30 AM - 06:30 PM"
            ),
            FirestoreClinicItem(
                id = "vet_6",
                placeId = "vet_6",
                name = "Dr. Rohan Kapoor",
                specialty = "Veterinary Cardiologist",
                address = "Heartland Animal Clinic, Golf Course Road, Gurugram",
                phone = "9822334455",
                rating = "4.7",
                distance = "3.8 KM Away",
                latitude = 28.4489,
                longitude = 77.0998,
                direction = "Heartland Animal Clinic, Golf Course Road, Gurugram",
                avatarBgHex = "#E8E2D9",
                imageUrl = "https://images.unsplash.com/photo-1582750433449-648ed127bb54?w=400",
                experience = "10 Years Experience",
                about = "Board-trained veterinary cardiologist offering echocardiography, ECG screening, and senior pet heart management.",
                openDays = "Monday - Friday",
                openHours = "10:30 AM - 06:00 PM"
            ),
            FirestoreClinicItem(
                id = "vet_7",
                placeId = "vet_7",
                name = "Dr. Priya Nair",
                specialty = "Emergency & Critical Care",
                address = "24x7 Pet Trauma Center, Sector 29, Gurugram",
                phone = "9899887766",
                rating = "4.8",
                distance = "2.7 KM Away",
                latitude = 28.4682,
                longitude = 77.0631,
                direction = "24x7 Pet Trauma Center, Sector 29, Gurugram",
                avatarBgHex = "#DDE7F0",
                imageUrl = "https://images.unsplash.com/photo-1651008376811-b90baee60c1f?w=400",
                experience = "7 Years Experience",
                about = "Round-the-clock emergency veterinarian handling trauma, poisoning, oxygen therapy, and ICU hospitalization.",
                openDays = "Monday - Sunday",
                openHours = "Open 24 Hours"
            ),
            FirestoreClinicItem(
                id = "vet_8",
                placeId = "vet_8",
                name = "Dr. Vikram Malhotra",
                specialty = "Orthopedic & Joint Specialist",
                address = "ActivePaws Ortho Clinic, Nirvana Country, Gurugram",
                phone = "9818765432",
                rating = "4.6",
                distance = "4.2 KM Away",
                latitude = 28.4191,
                longitude = 77.0698,
                direction = "ActivePaws Ortho Clinic, Nirvana Country, Gurugram",
                avatarBgHex = "#E8E2D9",
                imageUrl = "https://images.unsplash.com/photo-1618498082410-b4aa22193b38?w=400",
                experience = "11 Years Experience",
                about = "Specializes in fracture repair, hip dysplasia management, ligament reconstruction, and post-op physiotherapy.",
                openDays = "Monday - Saturday",
                openHours = "09:30 AM - 07:00 PM"
            ),
            FirestoreClinicItem(
                id = "vet_9",
                placeId = "vet_9",
                name = "Dr. Sneha Kulkarni",
                specialty = "Veterinary Dentist & Nutrition",
                address = "Happy Bark Dental & Wellness, Sohna Road, Gurugram",
                phone = "9876012345",
                rating = "4.5",
                distance = "4.5 KM Away",
                latitude = 28.4089,
                longitude = 77.0432,
                direction = "Happy Bark Dental & Wellness, Sohna Road, Gurugram",
                avatarBgHex = "#DDE7F0",
                imageUrl = "https://images.unsplash.com/photo-1527613426441-4da17471b66d?w=400",
                experience = "4 Years Experience",
                about = "Focuses on ultrasonic dental scaling, oral surgery, puppy vaccination schedules, and tailored weight-management diets.",
                openDays = "Wednesday - Monday",
                openHours = "10:00 AM - 07:30 PM"
            ),
            FirestoreClinicItem(
                id = "vet_10",
                placeId = "vet_10",
                name = "Dr. Kabir Bedi",
                specialty = "Ophthalmology & Internal Medicine",
                address = "ClearVision Pet Hospital, Palam Vihar, Gurugram",
                phone = "9958112233",
                rating = "4.8",
                distance = "5.0 KM Away",
                latitude = 28.5085,
                longitude = 77.0321,
                direction = "ClearVision Pet Hospital, Palam Vihar, Gurugram",
                avatarBgHex = "#E8E2D9",
                imageUrl = "https://images.unsplash.com/photo-1622902046580-2b47f47f5471?w=400",
                experience = "6 Years Experience",
                about = "Dedicated to pet eye care, cataract evaluations, and comprehensive internal medicine diagnostics.",
                openDays = "Monday - Saturday",
                openHours = "09:30 AM - 07:30 PM"
            )
        )

// Trigger once to upload all 10 into Firestore "clinics" collection:
        tenDoctorsList.forEach { viewModel.saveClinicToFirestore(it) }
    }
    Scaffold(
        containerColor = ScreenBgColor,
        bottomBar = {
            PetCareBottomNav(
                selectedIndex = uiState.selectedTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        }
    ) { paddingValues ->
        when (uiState.selectedTab) {
            0 -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    item {
                        TopHeroBanner(
                            userName = uiState.userName,
                            userLocation = uiState.userLocation
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        SectionHeader(title = "Services", onSeeAllClick = onSeeAllServicesClick)
                        Spacer(modifier = Modifier.height(14.dp))
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(uiState.services, key = { it.id }) { service ->
                                ServiceItem(service)
                            }
                        }
                    }

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
                                if (uiState.firestoreClinics.isNotEmpty()) {
                                    items(
                                        items = uiState.firestoreClinics.take(4),
                                        key = { it.id }
                                    ) { clinic ->
                                        FirestoreClinicCard(clinic = clinic)
                                    }
                                } else {
                                    items(
                                        items = uiState.vets.take(4),
                                        key = { it.id }
                                    ) { vet ->
                                        VetDoctorCard(
                                            vet = vet,
                                            onCardClick = { onDoctorClick(vet) }
                                        )
                                    }
                                }
//                                items(uiState.vets.take(4), key = { it.id }) { vet ->
//                                    VetDoctorCard(
//                                        vet = vet,
//                                        onCardClick = { onDoctorClick(vet) }
//                                    )
//                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                MessageScreen(
                    vets = uiState.vets,
                    modifier = Modifier.padding(paddingValues),
                    onDoctorClick = onDoctorClick
                )
            }

            2 -> {
                MapScreen(
                    vetsList = uiState.vets,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            3 -> {
                ProfileScreen(
                    modifier = Modifier.padding(paddingValues),
                    onAddDoctor = onAddDoctor
                )
            }
        }
    }
}

@Composable
private fun TopHeroBanner(
    userName: String,
    userLocation: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderBgColor)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                        text = "Hii $userName 👋",
                        color = PrimaryTeal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = userLocation,
                        color = Color(0xFF1F2933),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = DeepNavy,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

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
                    onClick = { },
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
        modifier = Modifier.clickable { }
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
fun VetDoctorCard(
    vet: VetDoctor,
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
                    onClick = { IntentUtils.openDialer(context, vet.phone) },
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
                    onClick = { IntentUtils.openGoogleMapsDirections(context, vet.direction) },
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

@Composable
fun InfoBadge(
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
        Pair("Map", Icons.Outlined.Map),
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

@Preview(showBackground = true)
@Composable
fun DashBoardScreenPreview() {
    DashBoardScreen()
}