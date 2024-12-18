package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class HomeMkViewModel (
    private val repositoryMk: RepositoryMk
): ViewModel(){

    val homeuiState: StateFlow<HomeMkUiState> = repositoryMk.getAllMk()
        .filterNotNull()
        .map {

        }
}




data class HomeMkUiState(
    val listMk: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)