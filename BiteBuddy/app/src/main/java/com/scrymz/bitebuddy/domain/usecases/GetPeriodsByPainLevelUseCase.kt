package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import javax.inject.Inject


class GetPeriodsByPainLevelUseCase @Inject constructor(
    private val repository: MenstrualRepository
) {
    operator fun invoke(painLevel: String) = repository.getPeriodsByPainLevel(painLevel)
}