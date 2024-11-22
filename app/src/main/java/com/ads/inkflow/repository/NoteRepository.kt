package com.ads.inkflow.repository

import com.ads.inkflow.data.NotaDatabaseDao
import com.ads.inkflow.model.Nota
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDatabaseDao: NotaDatabaseDao,
    private val firebaseFirestore: FirebaseFirestore

) {
    suspend fun addNote(nota: Nota) {
        noteDatabaseDao.insert(nota)

        firebaseFirestore.collection("notes")
            .document(nota.id)
            .set(nota)
    }

    suspend fun updateNote(nota: Nota) {
        noteDatabaseDao.update(nota)

        firebaseFirestore.collection("notes")
            .document(nota.id)
            .set(nota)
    }
    suspend fun deleteNote(nota: Nota) {
        noteDatabaseDao.deleteNote(nota)

        firebaseFirestore.collection("notes")
            .document(nota.id)
            .delete()
    }
    suspend fun deleteAllNotes() {
        noteDatabaseDao.deleteAll()
    }


    suspend fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<Nota>> {
        val firestoreNotes = firebaseFirestore.collection("notes")
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                val nota = doc.toObject(Nota::class.java)
                if (nota != null) {
                    nota.copy(id = doc.id)
                } else {
                    null
                }
            }



        // Adicionar notas do Firestore ao Room
        firestoreNotes.forEach { note ->
            noteDatabaseDao.insert(note) // Salva dados do firestore no banco local
        }
        // Mostra a lista do banco local ao abrir o app
        return noteDatabaseDao.getNotes()
    }
}