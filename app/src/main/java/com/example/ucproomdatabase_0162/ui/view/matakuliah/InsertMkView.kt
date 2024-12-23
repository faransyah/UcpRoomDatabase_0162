package com.example.ucproomdatabase_0162.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.ui.customwidget.TopAppBar
import com.example.ucproomdatabase_0162.ui.navigation.AlamatNavigasi
import com.example.ucproomdatabase_0162.ui.viewmodel.MataKuliahEvent
import com.example.ucproomdatabase_0162.ui.viewmodel.MataKuliahViewModel
import com.example.ucproomdatabase_0162.ui.viewmodel.MkFormErrorState
import com.example.ucproomdatabase_0162.ui.viewmodel.MkUIState
import com.example.ucproomdatabase_0162.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch
import java.text.Normalizer.Form

object DestinasiInsertMk: AlamatNavigasi{
    override val route: String = "insert_mk"
}

@Composable
fun InsertMkView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory )

){
    val uiState = viewModel.uiStateMk // Ambil UI state dai viewmodel
    val SnackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarMessageMatkul) {
        uiState.snackbarMessageMatkul?.let {message ->
            coroutineScope.launch {
                SnackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarMessage()
            }
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = SnackbarHostState) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah MataKuliah"
            )
            //isi body
            InsertBodyMk(
                uiState = uiState,
                dsnList = uiState.dsnList,
                onValuechange = {updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveDataMk()
                    }
                    onNavigate
                },

            )
        }

    }
}
@Composable
fun InsertBodyMk(
    modifier: Modifier = Modifier,
    onValuechange: (MataKuliahEvent) -> Unit,
    uiState: MkUIState,
    onClick: () -> Unit,
    dsnList: List<Dosen>
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMataKuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValuechange = onValuechange,
            errorState = uiState.isentryValidval,
            modifier = Modifier.fillMaxWidth(),
            dsnList = dsnList
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValuechange: (MataKuliahEvent) -> Unit,
    errorState: MkFormErrorState = MkFormErrorState(),
    modifier: Modifier = Modifier,
    dsnList: List<Dosen>
){
    val jenisMk = listOf("Peminatan", "Wajib")

    var chosenDropDown by remember { mutableStateOf(mataKuliahEvent.dosenPengampu)}

    var expanded by remember { mutableStateOf(false) }


    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange= {
                onValuechange(mataKuliahEvent.copy(kode = it))
            },
            label = {Text("Kode ")},
            isError = errorState.kode     != null,
            placeholder = { Text("Masukkan Kode")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.namaMk, onValueChange = {
                onValuechange(mataKuliahEvent.copy(namaMk = it))
            },
            label = {Text("Nama MataKuliah")},
            isError = errorState.namaMk   != null,
            placeholder = { Text("Masukkan Nama MataKuliah")},

        )
        Text(text = errorState.namaMk ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks, onValueChange = {
                onValuechange(mataKuliahEvent.copy(sks = it))},
            label = {Text("SKS")},
            isError = errorState.sks   != null,
            placeholder = { Text("Masukkan sks")},
        )
        Text(
            text = errorState.sks ?: "", color = Color.Red
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = mataKuliahEvent.semester,
                onValueChange= {
                    onValuechange(mataKuliahEvent.copy(semester = it))
                },
                label = {Text("Semester ")},
                isError = errorState.semester     != null,
                placeholder = { Text("Masukkan Semester")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text(
                text = errorState.semester ?: "",
                color = Color.Red
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Mata Kuliah")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisMk.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mataKuliahEvent.jenisMk == jk,
                        onClick = {
                            onValuechange(mataKuliahEvent.copy(jenisMk = jk))
                        },
                    )
                    Text(
                        text = jk,
                    )
                }
            }
        }
        Text(
            text = errorState.jenisMk ?: "",
            color = Color.Red
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded}
        ) {
            OutlinedTextField(
                value = chosenDropDown,
                onValueChange = {},
                label = { Text("Pilih Dosen Pengampu")},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand Menu"
                    )
                },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                isError = errorState.dosenPengampu != null
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false}
            ) {
                dsnList.forEach({ dosen ->
                    DropdownMenuItem(
                        onClick = {
                            chosenDropDown = dosen.nama
                            expanded = false
                            onValuechange(mataKuliahEvent.copy(dosenPengampu = dosen.nama))
                        },
                        text = { Text(text = dosen.nama )}
                    )

                })
            }

        }
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)


    }

}