package com.example.ucproomdatabase_0162.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mataKuliah")
data class MataKuliah(
    @PrimaryKey
    val kode: String,
    val namaMk: String,
    val sks: String,
    val semester: String,
    val jenisMk: String,
    val dosenPengampu: String
)