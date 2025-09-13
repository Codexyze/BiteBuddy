package com.scrymz.bitebuddy.domain.usecases

import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import javax.inject.Inject


class GetAllPeriodsDescendingUseCase @Inject constructor(
    private val repository: MenstrualRepository
) {
    operator fun invoke() = repository.getAllPeriodsDescending()
}
