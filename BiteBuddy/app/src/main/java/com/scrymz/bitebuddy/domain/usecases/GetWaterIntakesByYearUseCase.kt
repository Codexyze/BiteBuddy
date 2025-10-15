package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import javax.inject.Inject

class GetWaterIntakesByYearUseCase @Inject constructor(
    private val repository: WaterIntakeRepository
) {
    operator fun invoke(year: Int) = repository.getWaterIntakesByYear(year)
}