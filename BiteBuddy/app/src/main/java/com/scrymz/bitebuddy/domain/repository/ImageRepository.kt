package com.scrymz.bitebuddy.domain.repository

import com.scrymz.bitebuddy.data.local.model.model.ImageFolder
import com.scrymz.bitebuddy.data.local.model.model.Images
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getAllImage(): Flow<ResultState<List<Images>>>
    suspend fun getImageFolders(): Flow<ResultState<List<ImageFolder>>>
    suspend fun getImagesFromFolder(folderName: String): Flow<ResultState<List<Images>>>
}