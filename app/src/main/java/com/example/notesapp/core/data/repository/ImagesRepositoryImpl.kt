package com.example.notesapp.core.data.repository

import com.example.notesapp.core.data.mapper.toImages
import com.example.notesapp.core.data.remote.api.ImagesApi
import com.example.notesapp.core.domain.model.Images
import com.example.notesapp.core.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesApi: ImagesApi
) : ImagesRepository {

    override suspend fun searchImages(
        query: String
    ): Images? {
        return imagesApi.searchImages(query)?.toImages()
    }

}


