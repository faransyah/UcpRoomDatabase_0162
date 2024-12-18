package com.example.ucproomdatabase_0162.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MatakuliahDao { // Matakuliahdao "k" kecil
    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    // getAllMatakuliah
    @Query("SELECT * FROM mataKuliah ORDER BY namaMk ASC")
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    // get Matakuliah
    @Query("SELECT * FROM mataKuliah WHERE kode = :kode")
    fun getMataKuliah (kode: String): Flow<MataKuliah>

    // Delete Matakuliah
    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    // Update Matakuliah
    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
}