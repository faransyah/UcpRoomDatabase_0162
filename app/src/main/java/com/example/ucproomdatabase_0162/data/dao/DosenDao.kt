package com.example.ucproomdatabase_0162.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucproomdatabase_0162.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    // getAllDosen
    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen() : Flow<List<Dosen>>

    // getDosen
    @Query("SELECT * FROM dosen ORDER BY nidn = :nidn")
    fun getDosen (nidn: String): Flow<Dosen>
}