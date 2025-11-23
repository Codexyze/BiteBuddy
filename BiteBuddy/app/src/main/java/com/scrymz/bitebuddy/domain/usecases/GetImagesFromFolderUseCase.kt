package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.data.local.model.model.Images
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesFromFolderUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(folderName: String): Flow<ResultState<List<Images>>>{
        return imageRepository.getImagesFromFolder(folderName)
    }
}

