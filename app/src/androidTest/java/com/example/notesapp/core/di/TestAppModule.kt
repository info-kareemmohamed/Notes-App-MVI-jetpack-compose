package com.example.notesapp.core.di

import android.app.Application
import androidx.room.Room
import com.example.notesapp.core.data.local.NoteDb
import com.example.notesapp.core.data.remote.api.ImagesApi
import com.example.notesapp.core.data.repository.FakeImagesRepository
import com.example.notesapp.core.data.repository.FakeNoteRepository
import com.example.notesapp.core.domain.repository.ImagesRepository
import com.example.notesapp.core.domain.repository.NoteRepository
import com.example.notesapp.core.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDb(app: Application): NoteDb {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDb::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepository {
        return FakeNoteRepository()
    }

    @Provides
    @Singleton
    fun provideImageApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(): ImagesRepository {
        return FakeImagesRepository()
    }
}
