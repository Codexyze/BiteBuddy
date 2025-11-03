package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.data.entity.ImageToProgress
import com.scrymz.bitebuddy.domain.repository.ImageToProgressRepository
import javax.inject.Inject

class UpsertImageToProgressUseCase @Inject constructor(
    private val repository: ImageToProgressRepository
) {
    operator fun invoke(imageToProgress: ImageToProgress) = repository.upsertImageToProgress(imageToProgress)
}

