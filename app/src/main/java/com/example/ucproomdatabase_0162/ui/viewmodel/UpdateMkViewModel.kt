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

    var updateUIStateMk by mutableStateOf(MkUIState())
        private set

    var dsnList by mutableStateOf<List<Dosen>>(emptyList())
        private set


    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMk.KODE])

    init {
        viewModelScope.launch {
            updateUIStateMk =repositoryMk.getMk(_kode)
                .filterNotNull()
                .first()
                .toUIStateMk()
        }

        viewModelScope.launch {
            val dsnListFromRepo = repositoryDsn.getAllDsn().first()
            dsnList = dsnListFromRepo
                updateUIStateMk = updateUIStateMk.copy(dsnList = dsnListFromRepo)// Update UI State after fetching Dosen
            }
    }
    fun updateStateMk(mataKuliahEvent: MataKuliahEvent){
        println("Update Event : $mataKuliahEvent")
        updateUIStateMk = updateUIStateMk.copy(mataKuliahEvent = mataKuliahEvent)
    }

    fun validateFieldsMk(): Boolean{
        val event = updateUIStateMk.mataKuliahEvent
        val errorState = MkFormErrorState(
            kode = if(event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            namaMk = if(event.namaMk.isNotEmpty()) null else "Nama Matakuliah tidak boleh kosong",
            sks = if(event.sks.isNotEmpty()) null else "sks tidak boleh kosong",
            semester = if(event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenisMk = if(event.jenisMk.isNotEmpty()) null else "Kelas tidak boleh kosong",
            dosenPengampu = if(event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        updateUIStateMk = updateUIStateMk.copy(isentryValidval = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEventMk = updateUIStateMk.mataKuliahEvent

        if (validateFieldsMk()) {
            viewModelScope.launch {
                try {
                    // Menyimpan data tanpa mereset mataKuliahEvent
                    repositoryMk.updateMk(currentEventMk.toMataKuliahEntity())
                    updateUIStateMk = updateUIStateMk.copy(
                        snackbarMessageMatkul = "Data Berhasil DiUpdate",
                        // Jangan reset mataKuliahEvent jika ingin mempertahankan data yang baru
                        isentryValidval = MkFormErrorState()
                    )
                    println("SnackBarMessage diatur: ${updateUIStateMk.snackbarMessageMatkul}")
                } catch (e: Exception) {
                    updateUIStateMk = updateUIStateMk.copy(
                        snackbarMessageMatkul = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIStateMk = updateUIStateMk.copy(
                snackbarMessageMatkul = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIStateMk = updateUIStateMk.copy(snackbarMessageMatkul = null)
    }
}

fun MataKuliah.toUIStateMk(): MkUIState = MkUIState(
    mataKuliahEvent = this.toDetailUiEvent()
)




