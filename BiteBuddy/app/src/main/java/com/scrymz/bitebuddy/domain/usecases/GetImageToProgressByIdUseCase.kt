package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.ImageToProgressRepository
import javax.inject.Inject

class GetImageToProgressByIdUseCase @Inject constructor(
    private val repository: ImageToProgressRepository
) {
    operator fun invoke(id: Int) = repository.getImageToProgressById(id)
}

