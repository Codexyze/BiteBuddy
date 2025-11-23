package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.data.local.model.model.ImageFolder
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageFoldersUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(): Flow<ResultState<List<ImageFolder>>>{
        return imageRepository.getImageFolders()
    }
}

