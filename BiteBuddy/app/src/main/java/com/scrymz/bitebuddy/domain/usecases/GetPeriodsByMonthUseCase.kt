package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import javax.inject.Inject


class GetPeriodsByMonthUseCase @Inject constructor(
    private val repository: MenstrualRepository
) {
    operator fun invoke(month: Int, year: Int) = repository.getPeriodsByMonth(month, year)
}