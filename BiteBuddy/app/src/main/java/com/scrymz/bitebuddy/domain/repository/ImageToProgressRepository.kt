package com.scrymz.bitebuddy.domain.repository

import com.scrymz.bitebuddy.data.entity.ImageToProgress
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow

interface ImageToProgressRepository {
    fun upsertImageToProgress(imageToProgress: ImageToProgress): Flow<ResultState<String>>
    fun deleteImageToProgress(imageToProgress: ImageToProgress): Flow<ResultState<String>>
    fun getAllImageToProgressDescending(): Flow<ResultState<List<ImageToProgress>>>
    fun getImageToProgressByDate(date: String): Flow<ResultState<List<ImageToProgress>>>
    fun getImageToProgressById(id: Int): Flow<ResultState<ImageToProgress?>>
    fun getImageToProgressByAuthor(author: String): Flow<ResultState<List<ImageToProgress>>>
    fun deleteImageToProgressById(id: Int): Flow<ResultState<String>>
    fun getImageToProgressCount(): Flow<ResultState<Int>>
}

