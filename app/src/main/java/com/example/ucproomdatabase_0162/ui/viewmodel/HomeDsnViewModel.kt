package com.example.ucproomdatabase_0162.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.repository.RepositoryDsn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class HomeDsnViewModel (
    private val repositoryDsn: RepositoryDsn
): ViewModel(){




data class HomeUiState(
    val listDsn: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)