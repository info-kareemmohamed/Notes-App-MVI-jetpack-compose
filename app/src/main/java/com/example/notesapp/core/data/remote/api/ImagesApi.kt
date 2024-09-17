package com.example.notesapp.core.data.remote.api

import com.example.notesapp.core.data.remote.dto.ImageListDto
import com.example.notesapp.core.util.Constant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("/api/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("key") apiKey: String = API_KEY,
    ): ImageListDto?
}