package com.example.vetcare.view.Screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vetcare.R
import kotlinx.coroutines.delay
import kotlin.math.abs

private data class OnboardingPage(
    @DrawableRes val imageRes: Int,
    val titleLine1: String,
    val titleLine2Prefix: String,
    val highlightedWord: String,
    val description: String
)

private val AccentTeal = Color(0xFF1BB1D1)
private val HighlightPillBg = Color(0xFFDDF4FA)
private val TitleDark = Color(0xFF1A1A1A)
private val SubtitleGray = Color(0xFF6B7280)
private val InactiveDotColor = Color(0xFFD5E9EE)

@Composable
fun NavigationScreen(
    onGetStartedClick: () -> Unit = {}
) {
    val interBold = FontFamily(Font(R.font.inter_bold))

    val pages = listOf(
        OnboardingPage(
            imageRes = R.drawable.img,
            titleLine1 = "Take Better Care",
            titleLine2Prefix = "of Your ",
            highlightedWord = "Pets",
            description = "Find the perfect veterinarian and best services for\nyour small pets."
        ), OnboardingPage(
            imageRes = R.drawable.parent_icon,
            titleLine1 = "Book Trusted",
            titleLine2Prefix = "Vet ",
            highlightedWord = "Visits",
            description = "Schedule appointments, vaccinations, and health\ncheckups in just a few taps."
        ), OnboardingPage(
            imageRes = R.drawable.doctor_dog,
            titleLine1 = "Track Health &",
            titleLine2Prefix = "Daily ",
            highlightedWord = "Care",
            description = "Keep medical records, reminders, and pet tips\norganized in one place."
        )
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pages.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000L)
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % pages.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Image Pager
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1.15f)
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp)
                    .graphicsLayer {
                        alpha = (1f - abs(pageOffset)).coerceIn(0f, 1f)
                    }, contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = pages[page].imageRes),
                    contentDescription = "Onboarding illustration ${page + 1}",
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .fillMaxHeight(0.92f),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.BottomCenter
                )

                // Soft bottom fade into white background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.White)
                            )
                        )
                )
            }
        }

        // 2. Dynamic Title, Highlight Badge & Subtitle
        val currentPageData = pages[pagerState.currentPage]
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.55f)
                .padding(top = 16.dp)
        ) {
            Text(
                text = currentPageData.titleLine1,
                color = TitleDark,
                fontFamily = interBold,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = currentPageData.titleLine2Prefix,
                    color = TitleDark,
                    fontFamily = interBold,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.5).sp
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = HighlightPillBg, shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = currentPageData.highlightedWord,
                        color = DeepNavy,
                        fontFamily = interBold,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = currentPageData.description,
                color = SubtitleGray,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight.Normal
            )
        }

        // 3. Bottom Animated Indicator Dots & Get Started CTA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(pages.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    val dotWidth by animateDpAsState(
                        targetValue = if (isSelected) 18.dp else 7.dp, label = "dotWidth"
                    )
                    val dotColor by animateColorAsState(
                        targetValue = if (isSelected) AccentTeal else InactiveDotColor,
                        label = "dotColor"
                    )

                    Box(
                        modifier = Modifier
                            .width(dotWidth)
                            .height(7.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                    )
                }
            }

            Button(
                onClick = onGetStartedClick,
                colors = ButtonDefaults.buttonColors(containerColor = DeepNavy),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 22.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}