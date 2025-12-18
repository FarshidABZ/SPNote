package com.farshidabz.spnote.di

import com.farshidabz.spnote.data.notes.FakeNotesRepository
import com.farshidabz.spnote.domain.notes.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesModule {
    @Binds
    @Singleton
    abstract fun bindNotesRepository(
        impl: FakeNotesRepository
    ): NotesRepository
}