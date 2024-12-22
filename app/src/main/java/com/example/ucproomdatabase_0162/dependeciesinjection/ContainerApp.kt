package com.example.ucproomdatabase_0162.dependeciesinjection

import com.example.ucproomdatabase_0162.repository.LocalRepositoryDsn
import com.example.ucproomdatabase_0162.repository.RepositoryDsn
import com.example.ucproomdatabase_0162.repository.RepositoryMk
import android.content.Context
import com.example.myapplication.data.database.KrsDatabase
import com.example.ucproomdatabase_0162.repository.LocalRepositoryMk


interface InterfaceContainerApp {
    val repositoryDsn: RepositoryDsn
    val repositoryMk: RepositoryMk
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {

//    private val appContext = context.applicationContext

    override  val  repositoryDsn: RepositoryDsn by lazy {
        LocalRepositoryDsn(KrsDatabase.getDatabase(context).dosenDao())
    }

    override val repositoryMk: RepositoryMk by lazy {
        LocalRepositoryMk(KrsDatabase.getDatabase(context).matakuliah())
    }
}
