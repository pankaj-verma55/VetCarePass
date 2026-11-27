package com.example.vetcare.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vetcare.model.FirebaseRepository.FirestoreClinicRepository
import com.example.vetcare.model.FirebaseRepository.VetRepository
import com.example.vetcare.model.data.FirestoreClinicItem
import com.example.vetcare.model.data.ServiceCategory
import com.example.vetcare.model.data.VetDoctor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val userName: String = "Pankaj",
    val userLocation: String = "Gurugram, India",
    val selectedTab: Int = 0,
    val services: List<ServiceCategory> = emptyList(),
    val vets: List<VetDoctor> = emptyList(),
    val isLoading: Boolean = false,
    val firestoreClinics: List<FirestoreClinicItem> = emptyList(),
    val isSavingClinic: Boolean = false,
    val statusMessage: String? = null
)

class HomeViewModel(
    private val repository: VetRepository = VetRepository(),
    private val firestoreRepository: FirestoreClinicRepository = FirestoreClinicRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
        observeFirestoreClinics()
    }

    private fun loadDashboardData() {
        _uiState.update {
            it.copy(
                services = repository.getServices(),
//                vets = repository.getVets(),
                isLoading = false
            )
        }
    }

    private fun observeFirestoreClinics() {
        viewModelScope.launch {
            firestoreRepository.observeClinics().collect { clinics ->
                _uiState.update { it.copy(firestoreClinics = clinics) }
            }
        }
    }

    fun saveClinicToFirestore(clinic: FirestoreClinicItem) {
        _uiState.update { it.copy(isSavingClinic = true, statusMessage = null) }

        firestoreRepository.saveClinic(
            clinic = clinic,
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isSavingClinic = false,
                        statusMessage = "Clinic saved to Firestore"
                    )
                }
            },
            onError = { error ->
                _uiState.update {
                    it.copy(
                        isSavingClinic = false,
                        statusMessage = error
                    )
                }
            }
        )
    }

//    fun getVetById(vetId: String): VetDoctor {
//        return firestoreRepository.getVetById(vetId) ?: _uiState.value.vets.first()
//    }

    // Goes inside HomeViewModel (updates the bottom navigation tab in _uiState)
    fun selectTab(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    // Goes inside HomeViewModel (finds a clinic from the loaded _uiState list)
    fun getClinicById(clinicId: String): FirestoreClinicItem? {
        return _uiState.value.firestoreClinics.find { it.id == clinicId }
    }
}