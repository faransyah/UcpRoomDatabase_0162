package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.repository.RepositoryDsn
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import kotlinx.coroutines.launch

class MataKuliahViewModel(
    private val repositoryMk: RepositoryMk,
    private val repositoryDsn: RepositoryDsn

): ViewModel() {

   var uiStateMk by mutableStateOf(MkUIState())
       private set

    var dsnList by mutableStateOf<List<Dosen>>(emptyList())
        private set
    init {
    viewModelScope.launch {
        repositoryDsn.getAllDsn().collect { dosenList ->
            this@MataKuliahViewModel.dsnList = dosenList
            updateUiState() // Update UI State after fetching Dosen
        }
    }
}
    fun updateState (mataKuliahEvent: MataKuliahEvent){
        uiStateMk = uiStateMk.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }
    private fun validateFieldsMk(): Boolean{
        val event = uiStateMk.mataKuliahEvent
        val errorStateMk = MkFormErrorState(
            kode = if(event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            namaMk = if(event.namaMk.isNotEmpty()) null else "Nama Matakuliah tidak boleh kosong",
            sks = if(event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if(event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenisMk = if(event.jenisMk.isNotEmpty()) null else "Jenis MataKuliah boleh kosong",
            dosenPengampu = if(event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiStateMk = uiStateMk.copy(isentryValidval = errorStateMk)
        return errorStateMk.isValid()
    }
    fun saveDataMk() {
        val currentEvent = uiStateMk.mataKuliahEvent
        if (validateFieldsMk()) {
            viewModelScope.launch {
                try {
                    repositoryMk.insertMk(currentEvent.toMataKuliahEntity())
                    uiStateMk = uiStateMk.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(),  // Reset input form
                        isentryValidval = MkFormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiStateMk = uiStateMk.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateMk = uiStateMk.copy(
                snackbarMessage = "Input tidak valid, periksa kembali data anda."
            )
        }
    }

    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackbarMessage() {
        uiStateMk = uiStateMk.copy(snackbarMessage = null)
    }
    private fun updateUiState(){
        uiStateMk = uiStateMk.copy(dsnList = dsnList)
    }
}

data class MkUIState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isentryValidval : MkFormErrorState = MkFormErrorState(),
    val snackbarMessage: String? = null,
    val dsnList: List<Dosen> = emptyList()

)

data class MkFormErrorState(
    val kode: String? =  null,
    val namaMk: String? =  null,
    val sks: String? =  null,
    val semester: String? = null,
    val jenisMk: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && namaMk == null && sks == null &&
                semester == null && jenisMk == null && dosenPengampu == null
    }
}

data class MataKuliahEvent(
    val kode: String = "",
    val namaMk: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenisMk: String = "",
    val dosenPengampu: String = ""
)

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    namaMk = namaMk,
    sks = sks,
    semester = semester,
    jenisMk = jenisMk,
    dosenPengampu = dosenPengampu
)