package com.example.ucproomdatabase_0162.ui.navigation

interface AlamatNavigasi{
    val route: String
}

object DestinasiDetailDsn : AlamatNavigasi{
    override val route: "detail"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}