package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import javax.inject.Inject


class GetPeriodsByYearUseCase @Inject constructor(
    private val repository: MenstrualRepository
) {
    operator fun invoke(year: Int) = repository.getPeriodsByYear(year)
}