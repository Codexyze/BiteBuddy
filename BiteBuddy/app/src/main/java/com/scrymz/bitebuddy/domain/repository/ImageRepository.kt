package com.scrymz.bitebuddy.domain.repository

import com.scrymz.bitebuddy.data.local.model.model.Images
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getAllImage(): Flow<ResultState<List<Images>>>

}