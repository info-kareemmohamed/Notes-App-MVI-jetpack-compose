package com.example.notesapp.core.data.mapper

import com.example.notesapp.core.data.remote.dto.ImageListDto
import com.example.notesapp.core.domain.model.Images

fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { imageDto ->
            imageDto.previewURL ?: ""
        } ?: emptyList()
    )
}




