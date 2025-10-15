package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import javax.inject.Inject

class GetTotalWaterByYearUseCase @Inject constructor(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(year: Int) = repository.getTotalWaterByYear(year)
}