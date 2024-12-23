package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMkViewModel (
    private val repositoryMk: RepositoryMk
): ViewModel(){

    val homeuiStateMk: StateFlow<HomeMkUiState> = repositoryMk.getAllMk()
        .filterNotNull()
        .map {
            HomeMkUiState(
                listMka = it.toList(),
                isLoading = false,
            )

        }
        .onStart {
            emit(HomeMkUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeMkUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeMkUiState(
                isLoading = true,
            )

        )

}

data class HomeMkUiState(
    val listMka: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)