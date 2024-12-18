package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.repository.LocalRepositoryDsn
import com.example.ucproomdatabase_0162.repository.RepositoryDsn

class DosenViewModel(private val repositoryDsn: RepositoryDsn) : ViewModel(){


}




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