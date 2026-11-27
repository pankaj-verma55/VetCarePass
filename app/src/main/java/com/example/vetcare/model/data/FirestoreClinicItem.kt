package com.example.vetcare.model.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

//data class FirestoreClinicItem(
//    val id: String = "",
//    val placeId: String = "", // Compliant Google Place ID reference
//    val name: String = "",
//    val specialty: String = "Veterinary Clinic",
//    val address: String = "",
//    val phone: String = "",
//    val rating: String = "4.5",
//    val distance: String = "",
//    val latitude: Double = 0.0,
//    val longitude: Double = 0.0,
//    val createdAt: Long = 0L,
//    val direction: String = "",
//    val avatarBg: Color,
//    @DrawableRes val imageRes: Int,
//    val experience: String = "-",
//    val about: String = "-",
//    val openDays: String = "Monday - Saturday",
//    val openHours: String = "09:30 AM - 07:30 PM"
//
//)
import com.example.vetcare.R
import com.google.firebase.firestore.Exclude

data class FirestoreClinicItem(
    val id: String = "",
    val placeId: String = "",
    val name: String = "",
    val specialty: String = "Veterinary Clinic",
    val address: String = "",
    val phone: String = "",
    val rating: String = "4.5",
    val distance: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val createdAt: Long = 0L,
    val direction: String = "",
    val avatarBgHex: String = "#DDE7F0",
    val imageUrl: String = "",
    val experience: String = "-",
    val about: String = "-",
    val openDays: String = "Monday - Saturday",
    val openHours: String = "09:30 AM - 07:30 PM"
) {
    @get:Exclude
    val avatarBg: Color
        get() = try {
            Color(android.graphics.Color.parseColor(avatarBgHex))
        } catch (e: Exception) {
            Color(0xFFDDE7F0)
        }

    @get:Exclude
    @get:DrawableRes
    val fallbackImageRes: Int
        get() = when (id) {
            "vet_1", "vet_7" -> R.drawable.img_doctor
            "vet_2", "vet_8" -> R.drawable.img_doctor1
            "vet_3", "vet_9" -> R.drawable.img_doctor2
            "vet_4", "vet_10" -> R.drawable.img_doctor3
            "vet_5" -> R.drawable.img_doctor4
            else -> R.drawable.img_doctor5
        }
}