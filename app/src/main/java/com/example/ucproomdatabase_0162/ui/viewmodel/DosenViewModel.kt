package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.repository.LocalRepositoryDsn
import com.example.ucproomdatabase_0162.repository.RepositoryDsn

class DosenViewModel(private val repositoryDsn: RepositoryDsn) : ViewModel(){

    var uiState by mutableStateOf(DsnUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState (dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent,
        )
    }
    // validasi data input pengguna
    private fun validateFields(): Boolean{
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }


}





data class DsnUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)


data class FormErrorState(

    val nidn: String?=null,
    val nama: String?= null,
    val jenisKelamin: String?= null

){
    fun isValid(): Boolean{
        return nidn == null && nama == null && jenisKelamin == null
    }
}

data class DosenEvent(
    val nidn: String="",
    val nama: String="",
    val jenisKelamin: String=""
)

fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)