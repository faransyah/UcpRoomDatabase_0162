package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucproomdatabase_0162.data.dao.DosenDao
import com.example.ucproomdatabase_0162.data.dao.MatakuliahDao
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.data.entity.MataKuliah

@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase(){

    //mendefinisikan fungsi untuk mengakses data mahasiswa
    abstract fun dosenDao(): DosenDao
    abstract fun matakuliah(): MatakuliahDao

    companion object{
        @Volatile // Memastikan bahwa nilai variable Instance selalu sama di
        private  var  Instance: KrsDatabase? = null

        fun getDatabase(context: Context): KrsDatabase {
            return (Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    KrsDatabase::class.java, // Class database
                    "KrsDatabase" // Nama Database
                )
                    .build().also{ Instance = it }
            })
        }
    }
}