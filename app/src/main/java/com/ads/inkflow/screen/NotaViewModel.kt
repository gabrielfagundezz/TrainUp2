package com.ads.inkflow.screen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ads.inkflow.data.NotasDataSource
import com.ads.inkflow.model.Nota
import com.ads.inkflow.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotaViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Nota>>(emptyList())
    val noteList = _noteList.asStateFlow()
    //private var noteList = mutableStateListOf<Nota>()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged()
                .collect{ listOfNotes ->
                    if (listOfNotes.isNullOrEmpty()){
                        Log.d("Empty", ": Lista vazia")
                    }else{
                        _noteList.value = listOfNotes
                    }

                }
        }
        // noteList.addAll(NotasDataSource().carregarNotas())
    }

    fun addNote(note: Nota) = viewModelScope.launch { repository.addNote(note) }

    fun updateNote(note: Nota) = viewModelScope.launch {
        if (note.id != null) { // Certifique-se de que o ID existe
            repository.updateNote(note)
        } else {
            Log.d("NotaViewModel", "Erro: Nota sem ID para atualização.")
        }
    }

    fun removeNote(note: Nota) = viewModelScope.launch {
        Log.d("NotaViewModel", "Removendo nota: $note")
        repository.deleteNote(note)
    }




}