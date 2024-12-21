package com.example.ucproomdatabase_0162.ui.view.dosen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucproomdatabase_0162.data.entity.Dosen
import com.example.ucproomdatabase_0162.ui.customwidget.TopAppBar
import com.example.ucproomdatabase_0162.ui.viewmodel.HomeDsnUiState
import com.example.ucproomdatabase_0162.ui.viewmodel.HomeDsnViewModel
import com.example.ucproomdatabase_0162.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeDsnView(
    viewModel: HomeDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDsn: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Dosen",
                showBackButton = false,
                onBack = {},
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDsn,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Dosen",
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        val homeDosenUiState by viewModel.homeUiState.collectAsState()

        BodyHomeDsnView(
            homeUiState = homeDosenUiState,
            onClick = onDetailClick,
            snackbarHostState = snackbarHostState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeDsnView(
    homeUiState: HomeDsnUiState,
    onClick: (String) -> Unit = {},
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    when {
        homeUiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeUiState.isError -> {
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeUiState.listDsn.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dosen.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListDosen(
                listdsn = homeUiState.listDsn,
                onClick = onClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListDosen(
    listdsn: List<Dosen>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(listdsn) { dsn ->
            CardDsn(
                dsn = dsn,
                onClick = { onClick(dsn.nidn) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDsn(
    dsn: Dosen,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "Person Icon")
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = dsn.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date Range Icon")
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = dsn.nidn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
