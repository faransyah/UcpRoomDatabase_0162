package com.example.ucproomdatabase_0162.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.view.mahasiswa.HomeMkView
import com.example.ucproomdatabase_0162.ui.view.HomeMulai
import com.example.ucproomdatabase_0162.ui.view.dosen.DestinasiInsertDsn
import com.example.ucproomdatabase_0162.ui.view.dosen.HomeDsnView
import com.example.ucproomdatabase_0162.ui.view.dosen.InsertDsnView
import com.example.ucproomdatabase_0162.ui.view.matakuliah.DestinasiInsertMk
import com.example.ucproomdatabase_0162.ui.view.matakuliah.DetailMkView

import com.example.ucproomdatabase_0162.ui.view.matakuliah.InsertMkView
import com.example.ucproomdatabase_0162.ui.view.matakuliah.UpdateMkView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier : Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHome.route) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeMulai(
                onDsnCardClick = {
                    // Navigasi ke halaman HomeDsnView
                    navController.navigate(DestinasiHomeDsn.route)
                },
                onMkButton = {
                    // Navigasi ke halaman lain jika perlu
                    navController.navigate(DestinasiHomeMk.route)
                },
                modifier = modifier,
            )
        }

        // Halaman untuk HomeDsnView
        composable(
            route = DestinasiHomeDsn.route
        ) {
            HomeDsnView(
                onAddDsn = {
                    // Navigasi ke halaman InsertDsnView
                    navController.navigate(DestinasiInsertDsn.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        // Halaman untuk Home MK view
        composable(
            route = DestinasiHomeMk.route
        ) {
            HomeMkView(
                onDetailClick = {kode ->
                    navController.navigate("${DestinasiDetailMk.route}/$kode")
                    println(
                        "PengelolaHalaman: kode = $kode"
                    )
                },
                onAddMk ={
                    navController.navigate(DestinasiInsertMk.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }


        // Halaman InsertDsnView
        composable(
            route = DestinasiInsertDsn.route
        ) {
            InsertDsnView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        // insertMk
        composable(
            route = DestinasiInsertMk.route
        ) {
            InsertMkView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
        composable(
            DestinasiDetailMk.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMk.KODE){
                    type = NavType.StringType
                }
            )
        ) {
            val kode =it.arguments?.getString(DestinasiDetailMk.KODE)
            kode?.let { kode ->
                DetailMkView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMk.route}/$it")
                    },
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    modifier = modifier

                )
            }
        }
        //update matakuliah
        composable(
            route = DestinasiUpdateMk.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMk.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            val kode = it.arguments?.getString(DestinasiUpdateMk.KODE)
            kode?.let { kodeValue ->
                UpdateMkView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }


    }
}
