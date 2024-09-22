package com.example.notesapp.core.domain.repository

import com.example.notesapp.core.domain.model.Images


interface ImagesRepository {

    suspend fun searchImages(query: String): Images?

}