package com.ads.inkflow.data

import androidx.compose.runtime.MutableState
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ads.inkflow.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDatabaseDao {

    @Query("SELECT * from notas_tbl")
    fun getNotes(): Flow<List<Nota>>

    @Query("SELECT * from notas_tbl where id =:id")
    suspend fun getNoteById(id: String): Nota

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nota: Nota)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(nota: Nota)

    @Query("DELETE from notas_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(nota: Nota)

}
