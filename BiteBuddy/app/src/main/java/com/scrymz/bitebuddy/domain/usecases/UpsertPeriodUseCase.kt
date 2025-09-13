package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import javax.inject.Inject


class UpsertPeriodUseCase @Inject constructor(
    private val repository: MenstrualRepository
) {
    operator fun invoke(period: MenstrualPeriod) = repository.upsertPeriod(period)
}