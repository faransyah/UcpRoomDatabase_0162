package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import com.example.ucproomdatabase_0162.ui.navigation.DestinasiUpdateMk
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMk: RepositoryMk
): ViewModel(){

    var updateUIState by mutableStateOf(MkUIState())

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMk.KODE])


    init {
        viewModelScope.launch {
            updateUIState =repositoryMk.getMk(_kode)
                .filterNotNull()
                .first()
                .toUIStateMk()
        }
    }
    fun updateState(mataKuliahEvent: MataKuliahEvent){
        updateUIState = updateUIState.copy(
            mataKuliahEvent = mataKuliahEvent

        )
    }

    private fun validateFields(): Boolean{
        val event = updateUIState.mataKuliahEvent
        val errorState = MkFormErrorState(
            kode = if(event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            namaMk = if(event.namaMk.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if(event.sks.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            semester = if(event.semester.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jenisMk = if(event.jenisMk.isNotEmpty()) null else "Kelas tidak boleh kosong",
            dosenPengampu = if(event.dosenPengampu.isNotEmpty()) null else "Angkatan tidak boleh kosong"
        )
        updateUIState = updateUIState.copy(isentryValidval = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEventMk = updateUIState.mataKuliahEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMk.updateMk(currentEventMk.toMataKuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data Berhasil DiUpdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isentryValidval = MkFormErrorState()
                    )
                    println("SnackBarMessage diatur: ${updateUIState.snackbarMessage}")
                } catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data gagal diupdate"
                    )
                }
            }
        }else {
            updateUIState = updateUIState.copy(
                snackbarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackbarMessage = null)
    }
}

fun MataKuliah.toUIStateMk(): MkUIState = MkUIState(
    mataKuliahEvent = this.toDetailUiEvent()
)




