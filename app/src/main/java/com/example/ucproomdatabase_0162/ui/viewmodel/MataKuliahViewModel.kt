package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMk: RepositoryMk): ViewModel() {

    var uiState by mutableStateOf(MkUIState())

    fun updateState (mataKuliahEvent: MataKuliahEvent){
        uiState = uiState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }
    private fun validateFields(): Boolean{
        val event = uiState.mataKuliahEvent
        val errorState = MkFormErrorState(
            kode = if(event.kode.isNotEmpty()) null else "NIM tidak boleh kosong",
            namaMk = if(event.namaMk.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if(event.sks.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            semester = if(event.semester.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jenisMk = if(event.jenisMk.isNotEmpty()) null else "Kelas tidak boleh kosong",
            dosenPengampu = if(event.dosenPengampu.isNotEmpty()) null else "Angkatan tidak boleh kosong"
        )
        uiState = uiState.copy(isentryValidval = errorState)
        return errorState.isValid()
    }
    fun saveData() {
        val currentEvent = uiState.mataKuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMk.insertMk(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackbarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(),  // Reset input form
                        isentryValidval = MkFormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackbarMessage = "Input tidak valid, periksa kembali data anda."
            )
        }
    }

    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackbarMessage() {
        uiState = uiState.copy(snackbarMessage = null)
    }

}

data class MkUIState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isentryValidval : MkFormErrorState = MkFormErrorState(),
    val snackbarMessage: String? = null
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