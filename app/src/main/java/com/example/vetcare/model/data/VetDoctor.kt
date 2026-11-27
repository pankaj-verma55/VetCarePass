package com.example.vetcare.model.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class VetDoctor(
    val id: String="",
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
