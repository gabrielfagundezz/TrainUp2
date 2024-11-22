package com.ads.inkflow.di

import android.content.Context
import androidx.room.Room
import com.ads.inkflow.data.NotaDatabase
import com.ads.inkflow.data.NotaDatabaseDao
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(notaDatabase: NotaDatabase): NotaDatabaseDao
    = notaDatabase.notaDAO()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : NotaDatabase
    = Room.databaseBuilder(
        context,
        NotaDatabase::class.java,
        "notes_db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}