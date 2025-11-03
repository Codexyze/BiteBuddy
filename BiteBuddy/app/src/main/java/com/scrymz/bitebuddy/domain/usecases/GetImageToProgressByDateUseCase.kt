package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.ImageToProgressRepository
import javax.inject.Inject

class GetImageToProgressByDateUseCase @Inject constructor(
    private val repository: ImageToProgressRepository
) {
    operator fun invoke(date: String) = repository.getImageToProgressByDate(date)
}

