package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import javax.inject.Inject


class GetPeriodByIdUseCase @Inject constructor(
    private val repository: MenstrualRepository
) {
    operator fun invoke(id: Long) = repository.getPeriodById(id)
}