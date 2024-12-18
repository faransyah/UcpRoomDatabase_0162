package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.repository.RepositoryDsn
import com.example.ucproomdatabase_0162.ui.navigation.DestinasiDetailDsn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DetailDsnViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryDsn: RepositoryDsn,
): ViewModel(){
    private val _nidn: String = checkNotNull(savedStateHandle[DestinasiDetailDsn.NIDN])

    val detailUiState: StateFlow<DetailDsnUiState> = repositoryDsn.getDsn(_nidn)
        .filterNotNull()
        .map { DetailDsnUiState(
            detailUiEvent = it.toDetailUiEvent(),
            isLoading = false,
        )
        }
        .onStart {
            emit(DetailDsnUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailDsnUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(200),
            initialValue = DetailDsnUiState(
                isLoading = true,
            )
        )


}

data class DetailDsnUiState(
    val detailUiEvent: DosenEvent = DosenEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEvenEmpty: Boolean
        get() = detailUiEvent == DosenEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DosenEvent()
}

fun Dosen.toDetailUiEvent(): DosenEvent{
    return DosenEvent(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jenisKelamin

    )
}