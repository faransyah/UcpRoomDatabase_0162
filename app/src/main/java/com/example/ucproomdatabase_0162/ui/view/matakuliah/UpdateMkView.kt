package com.example.ucproomdatabase_0162.ui.view.matakuliah

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase_0162.ui.customwidget.TopAppBar
import com.example.ucproomdatabase_0162.ui.viewmodel.PenyediaViewModel
import com.example.ucproomdatabase_0162.ui.viewmodel.UpdateMkViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMkView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMkViewModel = viewModel(factory = PenyediaViewModel.Factory)
){

    val uiState = viewModel.updateUIState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(uiState.snackbarMessage){
        println("LaunchedEffect trigerred")
        uiState.snackbarMessage?.let{ message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine fo snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        topBar = {
            TopAppBar(
                judul = "Edit Matakuliah",
                showBackButton = true,
                onBack = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)

        ) {
            InsertBodyMk(
                uiState = uiState,
                onValuechange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFieldsMk()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                },
                dsnList = uiState.dsnList
            )
        }
    }

}