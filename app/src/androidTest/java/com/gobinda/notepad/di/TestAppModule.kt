package com.gobinda.notepad.di

import android.content.Context
import androidx.room.Room
import com.gobinda.notepad.data.repository.NoteRepository
import com.gobinda.notepad.data.repository.NoteRepositoryImpl
import com.gobinda.notepad.data.source.NoteDao
import com.gobinda.notepad.data.source.NoteDatabase
import com.gobinda.notepad.domain.usecase.AddNoteUseCase
import com.gobinda.notepad.domain.usecase.DeleteNoteUseCase
import com.gobinda.notepad.domain.usecase.GetNotesUseCase
import com.gobinda.notepad.domain.usecase.GetSingleNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * This is the test app module declaration, where we will basically replace all the
 * providers for testing. Like In a real app we should use persistent storage for
 * room database. But when testing we don't need persistent storage - we can confirm
 * our API by using InMemoryDatabase of room library. Plus we may provide some custom
 * implementation of the repository class, etc.
 */
@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
class TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            context = appContext,
            klass = NoteDatabase::class.java,
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

    @Provides
    fun providesDeleteNoteUseCase(repository: NoteRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }
}