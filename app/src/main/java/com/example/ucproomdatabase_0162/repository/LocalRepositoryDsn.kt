package com.example.ucproomdatabase_0162.repository

import com.example.ucproomdatabase_0162.data.dao.DosenDao
import com.example.ucproomdatabase_0162.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDsn (
    private val dosenDao : DosenDao
) :RepositoryDsn{
    override suspend fun insertDsn(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDsn(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    override fun getDsn(nidn: String): Flow<Dosen> {
        return dosenDao.getDosen(nidn)
    }


}