package com.ads.inkflow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ads.inkflow.data.NotasDataSource
import com.ads.inkflow.model.Nota
import com.ads.inkflow.screen.NotaViewModel
import com.ads.inkflow.screen.Screen1
import com.ads.inkflow.ui.theme.InkFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InkFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val notaViewModel = viewModel<NotaViewModel>()
                    //val notaViewModel: NotaViewModel by viewModels()
                    NotesApp(notaViewModel)

                }
            }
        }
    }
}

@Composable
fun NotesApp(notaViewModel: NotaViewModel){

    val notesList = notaViewModel.noteList.collectAsState().value

    Screen1(notas = notesList,
        onAddNota = {
            notaViewModel.addNote(it)
        },
        onRemoveNota = {
            notaViewModel.removeNote(it)
        },

        onUpdateNota = { notaViewModel.updateNote(it) }
        )

}