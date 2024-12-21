package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.repository.RepositoryDsn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDsnViewModel (
    private val repositoryDsn: RepositoryDsn
): ViewModel(){

    val homeUiState: StateFlow<HomeDsnUiState> = repositoryDsn.getAllDsn()
        .filterNotNull()
        .map {
            HomeDsnUiState(
                listDsn = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeDsnUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeDsnUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeDsnUiState(
                isLoading = true,
            )
        )
}


data class HomeDsnUiState(
    val listDsn: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)