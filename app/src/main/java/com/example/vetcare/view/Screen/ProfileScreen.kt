package com.example.vetcare.view.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBgColor)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5D0D6))
                .border(2.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🧑", fontSize = 40.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Pankaj",
            color = DeepNavy,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = "Sirsa, Haryana • Pet Parent",
            color = SubtextGray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Pet Info Card (Local, no login required)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = BorderStroke(1.dp, BorderGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "My Pet Details",
                    color = PrimaryTeal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Pet Type: Dog / Cat", color = DeepNavy, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Preferred City: Sirsa / Bengaluru", color = SubtextGray, fontSize = 13.sp)
            }
        }
    }
}