package com.example.vetcare.model.FirebaseRepository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.ui.graphics.Color
import com.example.vetcare.R
import com.example.vetcare.model.data.ServiceCategory
import com.example.vetcare.model.data.VetDoctor
import com.example.vetcare.ui.icon.PetCareIcons

class VetRepository {

    fun getServices(): List<ServiceCategory> = listOf(
        ServiceCategory("1", "Veterinary", PetCareIcons.VeterinaryBadge),
        ServiceCategory("2", "Food", PetCareIcons.FoodBowl),
        ServiceCategory("3", "Pharmacy", PetCareIcons.PharmacyCross),
        ServiceCategory("4", "Boarding", PetCareIcons.PetBoarding),
        ServiceCategory("5", "Grooming", Icons.Default.Pets)
    )

    fun getVets(): List<VetDoctor> = listOf(
        VetDoctor(
            id = "vet_1",
            name = "Dr. Michael Grossett",
            specialty = "Veterinary Neurosurgeon",
            rating = "4.1",
            distance = "1.2 KM Away",
            avatarBg = Color(0xFFDDE7F0),
            imageRes = R.drawable.img_doctor,
            phone = "9876543210",
            direction = "Dmc Hospital, Sirsa"
        ),
        VetDoctor(
            id = "vet_2",
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
            id = "vet_3",
            name = "Dr. Pankaj",
            specialty = "Animal Welfare",
            rating = "4.9",
            distance = "4.0 KM Away",
            avatarBg = Color(0xFFDDE7F0),
            imageRes = R.drawable.img_doctor2,
            phone = "9876543210",
            direction = "New Life Care Hospital, Sirsa"
        ),
        VetDoctor(
            id = "vet_4",
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
            id = "vet_5",
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
            id = "vet_6",
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

    fun getVetById(vetId: String): VetDoctor? = getVets().find { it.id == vetId }
}