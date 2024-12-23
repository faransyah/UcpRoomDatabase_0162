package com.example.ucproomdatabase_0162.repository

import com.example.ucproomdatabase_0162.data.dao.MatakuliahDao
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMk (
    private val matakuliahDao: MatakuliahDao
)   :RepositoryMk{
    override suspend fun insertMk(mataKuliah: MataKuliah) {
        matakuliahDao.insertMataKuliah(mataKuliah)
    }

    override fun getAllMk(): Flow<List<MataKuliah>> {
        return matakuliahDao.getAllMataKuliah()
    }

    override fun getMk(kode: String): Flow<MataKuliah> {
       return matakuliahDao.getMataKuliah(kode)
    }

    override suspend fun deleteMk(mataKuliah: MataKuliah) {
        matakuliahDao.deleteMataKuliah(mataKuliah)
    }

    override suspend fun updateMk(mataKuliah: MataKuliah) {
        matakuliahDao.deleteMataKuliah(mataKuliah)
    }

}