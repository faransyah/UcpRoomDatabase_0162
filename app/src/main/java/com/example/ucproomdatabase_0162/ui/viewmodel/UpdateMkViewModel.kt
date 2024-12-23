package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.repository.RepositoryDsn
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import com.example.ucproomdatabase_0162.ui.navigation.DestinasiUpdateMk
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMk: RepositoryMk,
    private val repositoryDsn: RepositoryDsn
): ViewModel(){

    var updateUIState by mutableStateOf(MkUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMk.KODE])
    var dsnList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            updateUIState =repositoryMk.getMk(_kode)
                .filterNotNull()
                .first()
                .toUIStateMk()
        }

        viewModelScope.launch {
            val dsnListFromRepo = repositoryDsn.getAllDsn().first()
            dsnList = dsnListFromRepo
                updateUIState = updateUIState.copy(dsnList = dsnListFromRepo)// Update UI State after fetching Dosen
            }
    }
    fun updateState(mataKuliahEvent: MataKuliahEvent){
        updateUIState = updateUIState.copy(
            mataKuliahEvent = mataKuliahEvent

        )
    }

    fun validateFieldsMk(): Boolean{
        val event = updateUIState.mataKuliahEvent
        val errorState = MkFormErrorState(
            kode = if(event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            namaMk = if(event.namaMk.isNotEmpty()) null else "Nama Matakuliah tidak boleh kosong",
            sks = if(event.sks.isNotEmpty()) null else "sks tidak boleh kosong",
            semester = if(event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenisMk = if(event.jenisMk.isNotEmpty()) null else "Kelas tidak boleh kosong",
            dosenPengampu = if(event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        updateUIState = updateUIState.copy(isentryValidval = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEventMk = updateUIState.mataKuliahEvent

        if (validateFieldsMk()){
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




