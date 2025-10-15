package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import javax.inject.Inject

class GetTotalWaterByDateUseCase @Inject constructor(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(date: String) = repository.getTotalWaterByDate(date)
}