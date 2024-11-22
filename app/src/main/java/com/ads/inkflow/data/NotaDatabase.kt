package com.ads.inkflow.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ads.inkflow.model.Nota
import com.ads.inkflow.util.DateConverter
import com.ads.inkflow.util.UUIDConverter

@Database(entities = [Nota::class], version = 2, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NotaDatabase: RoomDatabase(){

    abstract fun notaDAO(): NotaDatabaseDao


}