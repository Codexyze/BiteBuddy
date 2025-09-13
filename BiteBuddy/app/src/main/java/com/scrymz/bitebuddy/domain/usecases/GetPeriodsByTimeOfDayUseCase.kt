package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import javax.inject.Inject


class GetPeriodsByTimeOfDayUseCase @Inject constructor(
    private val repository: MenstrualRepository
) {
    operator fun invoke(timeOfDay: String) = repository.getPeriodsByTimeOfDay(timeOfDay)
}