package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import javax.inject.Inject

class GetWaterIntakeByIdUseCase @Inject constructor(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(id: Long) = repository.getWaterIntakeById(id)
}