package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import com.example.ucproomdatabase_0162.ui.navigation.DestinasiDetailMk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMk: RepositoryMk,
): ViewModel(){
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMk.KODE])

    val detailUiState: StateFlow<DetailMkUiState> = repositoryMk.getMk(_kode)
        .filterNotNull()
        .map { DetailMkUiState(
            detailUiEvent = it.toDetailUiEvent(),
            isLoading = false,
        )
        }
        .onStart {
            emit(DetailMkUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailMkUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailMkUiState(
                isLoading = true,
            ),
        )
    fun deleteMhs(){
        detailUiState.value.detailUiEvent.toMataKuliahEntity()
            .let {
                viewModelScope.launch {
                    repositoryMk.deleteMk(it)
                }
            }
    }

}




data class DetailMkUiState(
    val detailUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MataKuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MataKuliahEvent()
}


fun MataKuliah.toDetailUiEvent(): MataKuliahEvent{
    return MataKuliahEvent(

        kode = kode,
        namaMk = namaMk,
        sks = sks,
        semester = semester,
        jenisMk = jenisMk,
        dosenPengampu = dosenPengampu
    )
}