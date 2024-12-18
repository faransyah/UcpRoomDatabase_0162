package com.example.ucproomdatabase_0162.ui.view.dosen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase_0162.ui.navigation.AlamatNavigasi
import com.example.ucproomdatabase_0162.ui.viewmodel.DosenViewModel
import com.example.ucproomdatabase_0162.ui.viewmodel.PenyediaViewModel

object DestinasiInsertDsn : AlamatNavigasi{
    override val route: String = "insert_dsn"
}

@Composable
fun InsertDsnView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DosenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){

}
