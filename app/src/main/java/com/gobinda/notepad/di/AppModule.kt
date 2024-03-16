package com.gobinda.notepad.di

import android.content.Context
import androidx.room.Room
import com.gobinda.notepad.data.repository.NoteRepository
import com.gobinda.notepad.data.repository.NoteRepositoryImpl
import com.gobinda.notepad.data.source.NoteDao
import com.gobinda.notepad.data.source.NoteDatabase
import com.gobinda.notepad.domain.usecase.AddNoteUseCase
import com.gobinda.notepad.domain.usecase.GetNotesUseCase
import com.gobinda.notepad.domain.usecase.GetSingleNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = NoteDatabase::class.java,
            name = NoteDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    fun provideGetNoteListUseCase(repository: NoteRepository): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }

    @Provides
    fun provideSingleNoteUseCase(repository: NoteRepository): GetSingleNoteUseCase {
        return GetSingleNoteUseCase(repository)
    }

    @Provides
    fun provideAddNoteUseCase(repository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }
}