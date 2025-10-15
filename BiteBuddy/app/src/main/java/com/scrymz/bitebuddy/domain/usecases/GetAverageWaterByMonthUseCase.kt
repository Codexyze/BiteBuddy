package com.scrymz.bitebuddy.domain.usecases



import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import javax.inject.Inject

class GetAverageWaterByMonthUseCase @Inject constructor(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(month: Int, year: Int) = repository.getAverageWaterByMonth(month, year)
}