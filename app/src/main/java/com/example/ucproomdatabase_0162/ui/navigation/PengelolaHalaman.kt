package com.example.ucproomdatabase_0162.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ucproomdatabase_0162.ui.view.HomeMulai
import com.example.ucproomdatabase_0162.ui.view.dosen.DestinasiInsertDsn
import com.example.ucproomdatabase_0162.ui.view.dosen.HomeDsnView
import com.example.ucproomdatabase_0162.ui.view.dosen.InsertDsnView

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
                onDsnButton = {
                    // Navigasi ke halaman HomeDsnView
                    navController.navigate(DestinasiHomeDsn.route)
                },
                onMkButton = {
                    // Navigasi ke halaman lain jika perlu
                    navController.popBackStack()
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
                onDetailClick = { nidn ->
                    // Navigasi ke halaman detail dosen jika diperlukan
                    navController.navigate("detail_dosen/$nidn")
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
    }
}
