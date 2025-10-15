package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.data.entity.WaterIntake
import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import javax.inject.Inject

class DeleteWaterIntakeUseCase @Inject constructor(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(waterIntake: WaterIntake) = repository.deleteWaterIntake(waterIntake)
}