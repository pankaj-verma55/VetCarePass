package com.example.vetcare.ViewModel

import androidx.lifecycle.ViewModel
import com.example.vetcare.model.FirebaseRepository.VetRepository
import com.example.vetcare.model.data.ServiceCategory
import com.example.vetcare.model.data.VetDoctor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val userName: String = "Pankaj",
    val userLocation: String = "Gurugram, India",
    val selectedTab: Int = 0,
    val services: List<ServiceCategory> = emptyList(),
    val vets: List<VetDoctor> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel(
    private val repository: VetRepository = VetRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _uiState.update {
            it.copy(
                services = repository.getServices(),
                vets = repository.getVets(),
                isLoading = false
            )
        }
    }

    fun selectTab(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun getVetById(vetId: String): VetDoctor {
        return repository.getVetById(vetId) ?: _uiState.value.vets.first()
    }
}