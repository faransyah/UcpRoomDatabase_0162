package com.example.ucproomdatabase_0162.ui.view.matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase_0162.data.entity.MataKuliah
import com.example.ucproomdatabase_0162.ui.viewmodel.DetailMkUiState
import com.example.ucproomdatabase_0162.ui.viewmodel.DetailMkViewModel
import com.example.ucproomdatabase_0162.ui.viewmodel.PenyediaViewModel
import com.example.ucproomdatabase_0162.ui.viewmodel.toMataKuliahEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMkView(
    modifier: Modifier = Modifier,
    viewModel: DetailMkViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}

){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.kode) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize() // Agar Column mengisi seluruh ruang yang ada
                .padding(innerPadding) // Mengatur padding keseluruhan
        ) {
            // TopAppBar
            com.example.ucproomdatabase_0162.ui.customwidget.TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah MataKuliah"
            )

            // BodyDetailMk
            BodyDetailMk(
                modifier = Modifier
                    .fillMaxSize() // Agar BodyDetailMk mengisi sisa ruang
                    .padding(16.dp), // Padding tambahan untuk BodyDetailMk
                detailMkUiState = detailUiState,
                onDeleteClick = {
                    viewModel.deleteMhs()
                    onDeleteClick()
                }
            )
        }
    }
}
@Composable
fun BodyDetailMk(
    modifier: Modifier = Modifier,
    detailMkUiState: DetailMkUiState = DetailMkUiState(),
    onDeleteClick: () -> Unit = {},



){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        detailMkUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Show loading indicator
            }
        }
        detailMkUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMk(
                    mataKuliah = detailMkUiState.detailUiEvent.toMataKuliahEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
                if (deleteConfirmationRequired) {
                    DeleteConFirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailMkUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMk(
    modifier: Modifier = Modifier,
    mataKuliah: MataKuliah
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMk(judul = "KODE MK", isinya = mataKuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMk(judul = "Nama Mata Kuliah", isinya = mataKuliah.namaMk)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMk(judul = "sks", isinya = mataKuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMk(judul = "Semester", isinya = mataKuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMk(judul = "Jenis MataKuliah", isinya = mataKuliah.jenisMk)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMk(judul = "Dosen Pengampu", isinya = mataKuliah.dosenPengampu)
            Spacer(modifier = Modifier.padding(4.dp))
        }

    }
}

@Composable
fun ComponentDetailMk(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
@Composable
private fun DeleteConFirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )

}