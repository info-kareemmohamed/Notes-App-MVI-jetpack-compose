package com.example.notesapp.core.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.core.data.local.NoteDao
import com.example.notesapp.core.data.local.NoteDb
import com.example.notesapp.core.data.remote.api.ImagesApi
import com.example.notesapp.core.data.repository.NoteRepositoryImpl
import com.example.notesapp.core.domain.repository.NoteRepository
import com.example.notesapp.core.util.Constant.BASE_URL
import com.example.notesapp.core.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideNoteDb(app: Application): NoteDb {
        return Room.databaseBuilder(
            app,
            NoteDb::class.java,
            DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDb: NoteDb) = noteDb.noteDao

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepositoryImpl(noteDao)


    @Provides
    @Singleton
    fun provideImageApi(): ImagesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImagesApi::class.java)
    }


}