package com.example.ucproomdatabase_0162

import android.app.Application
import com.example.ucproomdatabase_0162.dependeciesinjection.ContainerApp

class KrsApp : Application(){
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}