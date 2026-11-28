package com.example.vetcare.utils

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Dashboard : Screen("dashboard")
    data object VeterinaryNearYou : Screen("veterinary_near_you")
    data object ProvidesServices : Screen("provides_services")
    data object Appointment : Screen("appointment/{vetId}") {
        fun createRoute(vetId: String) = "appointment/$vetId"
    }
    data object AddClinic : Screen("add_clinic")
}