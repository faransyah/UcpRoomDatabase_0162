package com.example.ucproomdatabase_0162.repository

import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMk {
    suspend fun insertMk(mataKuliah: MataKuliah)

    fun getAllMk() : Flow<List<MataKuliah>>

    fun getMk(kode: String) : Flow<MataKuliah>

    // Mk "k" kecil
    suspend fun deleteMk(mataKuliah: MataKuliah)

    suspend fun updateMk(mataKuliah: MataKuliah)
}