package com.example.ucproomdatabase_0162.ui.navigation

interface AlamatNavigasi{
    val route: String
}
object DestinasiHome : AlamatNavigasi{
    override val route = "home"
}
object DestinasiHomeDsn : AlamatNavigasi{
    override val route = "home_dsn"
}
object DestinasiDetailMk : AlamatNavigasi{
    override val route = "detailmk"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMk : AlamatNavigasi{
    override val route = "update"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}