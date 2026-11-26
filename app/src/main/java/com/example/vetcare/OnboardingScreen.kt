//package com.example.vetcare
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//private val DeepNavy = Color(0xFF1D3B68)
//private val AccentTeal = Color(0xFF1BB1D1)
//private val ArchFillBlue = Color(0xFFD2F1F9)
//private val ArchBorderBlue = Color(0xFFE3F6FB)
//private val HighlightPillBg = Color(0xFFDDF4FA)
//private val TitleDark = Color(0xFF1A1A1A)
//private val SubtitleGray = Color(0xFF6B7280)
//private val InactiveDotColor = Color(0xFFD5E9EE)
//
//@Composable
//fun OnboardingScreen(
//    onGetStartedClick: () -> Unit = {}
//) {
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color.White
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .statusBarsPadding()
//                .navigationBarsPadding()
//                .padding(horizontal = 24.dp, vertical = 20.dp),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            // 1. Arched Pet Hero Illustration
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1.15f)
//                    .padding(top = 12.dp),
//                contentAlignment = Alignment.BottomCenter
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.img),
//                    contentDescription = "Dog with stethoscope and cat",
//                    modifier = Modifier
//                        .fillMaxWidth(0.95f)
//                        .fillMaxHeight(0.92f),
//                    contentScale = ContentScale.Fit,
//                    alignment = Alignment.BottomCenter
//                )
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(36.dp)
//                        .align(Alignment.BottomCenter)
//                        .background(
//                            Brush.verticalGradient(
//                                colors = listOf(Color.Transparent, Color.White)
//                            )
//                        )
//                )
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // 2. Headline & Subtitle Section
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.55f)
//            ) {
//                Text(
//                    text = "Take Better Care",
//                    color = TitleDark,
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.ExtraBold,
//                    letterSpacing = (-0.5).sp
//                )
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = "of Your ",
//                        color = TitleDark,
//                        fontSize = 30.sp,
//                        fontWeight = FontWeight.ExtraBold,
//                        letterSpacing = (-0.5).sp
//                    )
//
//                    // Highlighted "Pets" badge
//                    Box(
//                        modifier = Modifier
//                            .background(
//                                color = HighlightPillBg,
//                                shape = RoundedCornerShape(10.dp)
//                            )
//                            .padding(horizontal = 10.dp, vertical = 2.dp)
//                    ) {
//                        Text(
//                            text = "Pets",
//                            color = DeepNavy,
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.ExtraBold,
//                            letterSpacing = (-0.5).sp
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(14.dp))
//
//                Text(
//                    text = "Find perfect veterinarian and best services for\nyour small pets.",
//                    color = SubtitleGray,
//                    fontSize = 14.sp,
//                    lineHeight = 21.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            }
//
//            // 3. Bottom Indicator Dots & Get Started CTA
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 12.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Page Indicator Dots (Active capsule + 2 inactive circles)
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(6.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .width(18.dp)
//                            .height(7.dp)
//                            .clip(CircleShape)
//                            .background(AccentTeal)
//                    )
//                    Box(
//                        modifier = Modifier
//                            .size(7.dp)
//                            .clip(CircleShape)
//                            .background(InactiveDotColor)
//                    )
//                    Box(
//                        modifier = Modifier
//                            .size(7.dp)
//                            .clip(CircleShape)
//                            .background(InactiveDotColor)
//                    )
//                }
//
//                // Get Started Button
//                Button(
//                    onClick = onGetStartedClick,
//                    colors = ButtonDefaults.buttonColors(containerColor = DeepNavy),
//                    shape = RoundedCornerShape(12.dp),
//                    contentPadding = PaddingValues(horizontal = 22.dp, vertical = 14.dp)
//                ) {
//                    Text(
//                        text = "Get Started",
//                        color = Color.White,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                        contentDescription = null,
//                        tint = Color.White,
//                        modifier = Modifier.size(18.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun OnboardingScreenPreview() {
//    OnboardingScreen()
//}