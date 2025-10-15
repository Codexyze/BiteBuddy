package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import javax.inject.Inject

class GetWaterIntakesByMonthUseCase @Inject constructor(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(month: Int, year: Int) = repository.getWaterIntakesByMonth(month, year)
}