package com.example.vetcare.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vetcare.view.Screen.AppointmentScreen
import com.example.vetcare.view.Screen.NavigationScreen
import com.example.vetcare.view.Screen.DashBoardScreen
import com.example.vetcare.view.Screen.ProvidesServices
import com.example.vetcare.view.Screen.VeterinaryNearYou
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.vetcare.ViewModel.HomeViewModel
import com.example.vetcare.view.Screen.AddDoctorClinicScreen

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

@Composable
fun AppNavigation(
    homeViewModel: HomeViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by homeViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(route = Screen.Onboarding.route) {
            NavigationScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

//        composable(route = Screen.Dashboard.route) {
//            DashBoardScreen(
//                viewModel = homeViewModel,
//                onSeeAllServicesClick = {
//                    navController.navigate(Screen.ProvidesServices.route)
//                },
//                onSeeAllVeterinaryClick = {
//                    navController.navigate(Screen.VeterinaryNearYou.route)
//                },
//                onDoctorClick = { selectedVet ->
//                    navController.navigate(Screen.Appointment.createRoute(selectedVet.id))
//                },
//                onAddDoctor = {
//                    navController.navigate(Screen.AddClinic.route)
//                }
//            )
//        }
        composable(route = Screen.Dashboard.route) {
            DashBoardScreen(
                viewModel = homeViewModel,
                onSeeAllServicesClick = {
                    navController.navigate(Screen.ProvidesServices.route)
                },
                onSeeAllVeterinaryClick = {
                    navController.navigate(Screen.VeterinaryNearYou.route)
                },
                onDoctorClick = { selectedVet ->
                    navController.navigate(Screen.Appointment.createRoute(selectedVet.id))
                },
                onAddDoctor = {
                    navController.navigate(Screen.AddClinic.route)
                }
            )
        }

        composable(route = Screen.ProvidesServices.route) {
            ProvidesServices(
                servicesList = uiState.services,
                onBackClick = { navController.popBackStack() }
            )
        }

//        composable(route = Screen.VeterinaryNearYou.route) {
//            VeterinaryNearYou(
//                onBackClick = { navController.popBackStack() },
//                onDoctorClick = { selectedVet ->
//                    navController.navigate(Screen.Appointment.createRoute(selectedVet.id))
//                }
//            )
//        }

        composable(route = Screen.VeterinaryNearYou.route) {
            VeterinaryNearYou(
                viewModel = homeViewModel,
                onBackClick = { navController.popBackStack() },
                onDoctorClick = { selectedVet ->
                    val targetId = selectedVet.id.ifBlank { selectedVet.placeId }
                    if (targetId.isNotBlank()) {
                        navController.navigate(Screen.Appointment.createRoute(targetId))
                    }
                }
            )
        }

        composable(route = Screen.AddClinic.route) {
            AddDoctorClinicScreen(
                homeViewModel = homeViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

//        composable(
//            route = Screen.Appointment.route,
//            arguments = listOf(navArgument("vetId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val vetId = backStackEntry.arguments?.getString("vetId") ?: ""
//            val vet = homeViewModel.getClinicById(vetId)
//
//            AppointmentScreen(
//                vet = vet,
//                onBackClick = { navController.popBackStack() },
//                onBookNowSuccess = { navController.popBackStack() }
//            )
//        }

        composable(
            route = Screen.Appointment.route,
            arguments = listOf(navArgument("vetId") { type = NavType.StringType })
        ) { backStackEntry ->
            val vetId = backStackEntry.arguments?.getString("vetId") ?: ""
            val vet = uiState.firestoreClinics.find {
                it.id == vetId || it.placeId == vetId
            }

            AppointmentScreen(
                vet = vet,
                onBackClick = { navController.popBackStack() },
                onBookNowSuccess = { navController.popBackStack() }
            )
        }
    }
}