package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.ImageToProgressRepository
import javax.inject.Inject

class GetAllImageToProgressDescendingUseCase @Inject constructor(
    private val repository: ImageToProgressRepository
) {
    operator fun invoke() = repository.getAllImageToProgressDescending()
}

