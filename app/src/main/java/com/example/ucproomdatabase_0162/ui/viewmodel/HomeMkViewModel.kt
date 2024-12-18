package com.example.ucproomdatabase_0162.ui.viewmodel

import com.example.ucproomdatabase_0162.data.entity.MataKuliah

data class HomeMkUiState(
    val listMk: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)