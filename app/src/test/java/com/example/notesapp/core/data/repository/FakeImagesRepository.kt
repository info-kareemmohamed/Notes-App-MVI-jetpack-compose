package com.example.notesapp.core.data.repository

import com.example.notesapp.core.domain.model.Images
import com.example.notesapp.core.domain.repository.ImagesRepository


class FakeImagesRepository : ImagesRepository {

    private var shouldReturnError = false
    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun searchImages(
        query: String
    ): Images? {
        return if (shouldReturnError) {
            null
        } else {
            Images(
                listOf("image1", "image2", "image3", "image4")
            )
        }
    }
}


















