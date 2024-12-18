package com.example.ucproomdatabase_0162.ui.viewmodel

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucproomdatabase_0162.KrsApp

object PenyediaViewModel{

    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                KrsApp().containerApp.repositoryDsn
            )
        }
        initializer {
            HomeDsnViewModel(
                KrsApp().containerApp.repositoryDsn
            )
        }
        initializer {
            DetailDsnViewModel(
                createSavedStateHandle(),
                KrsApp().containerApp.repositoryDsn
            )
        }
    }
}

fun CreationExtras.KrsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as KrsApp)
