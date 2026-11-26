package com.example.vetcare.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vetcare.R
import com.example.vetcare.view.Screen.AppointmentScreen
import com.example.vetcare.view.Screen.NavigationScreen
import com.example.vetcare.view.Screen.PetCareHomeScreen
import com.example.vetcare.view.Screen.ProvidesServices
import com.example.vetcare.view.Screen.VetDoctor
import com.example.vetcare.view.Screen.VeterinaryNearYou
import com.example.vetcare.view.Screen.services

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Dashboard : Screen("dashboard")
    data object VeterinaryNearYou : Screen("veterinary_near_you")
    data object ProvidesServices : Screen("provides_services")
    data object Appointment : Screen("appointment/{vetIndex}") {
        fun createRoute(vetIndex: Int) = "appointment/$vetIndex"
    }
}

val vets = listOf(
    VetDoctor(
        name = "Dr. Michael Grossett",
        specialty = "Veterinary Neurosurgeon",
        rating = "4.1",
        distance = "1.2 KM Away",
        avatarBg = Color(0xFFDDE7F0),
        imageRes = R.drawable.img_doctor,
        phone = "9876543210",
        direction = "Dmc Hospital,sirsa"
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
        direction = "New Life Care Hospital,Sirsa"
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
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(route = Screen.Onboarding.route) {
            NavigationScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Single Dashboard destination with BOTH callbacks wired up
        composable(route = Screen.Dashboard.route) {
            PetCareHomeScreen(
                onSeeAllServicesClick = {
                    navController.navigate(Screen.ProvidesServices.route)
                },
                onSeeAllVeterinaryClick = {
                    navController.navigate(Screen.VeterinaryNearYou.route)
                },
                onDoctorClick = { selectedVet ->
                    val index = vets.indexOf(selectedVet).coerceAtLeast(0)
                    navController.navigate(Screen.Appointment.createRoute(index))
                }
            )
        }

        // Uncommented destination for Services "See All"
        composable(route = Screen.ProvidesServices.route) {
            ProvidesServices(
                servicesList = services,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.VeterinaryNearYou.route) {
            VeterinaryNearYou(
                vets = vets,
                onBackClick = {
                    navController.popBackStack()
                },
                onDoctorClick = { selectedVet ->
                    val index = vets.indexOf(selectedVet).coerceAtLeast(0)
                    navController.navigate(Screen.Appointment.createRoute(index))
                }
            )
        }
        composable(route = Screen.Appointment.route) { backStackEntry ->
            val vetIndex = backStackEntry.arguments
                ?.getString("vetIndex")
                ?.toIntOrNull()
                ?.coerceIn(0, vets.lastIndex) ?: 0

            AppointmentScreen(
                vet = vets[vetIndex],
                onBackClick = { navController.popBackStack() },
                onBookNowSuccess = { navController.popBackStack() }
            )
        }
    }
}