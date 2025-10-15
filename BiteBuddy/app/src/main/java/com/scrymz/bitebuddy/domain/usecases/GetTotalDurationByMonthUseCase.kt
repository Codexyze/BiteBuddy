package com.scrymz.bitebuddy.domain.usecases



import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetTotalDurationByMonthUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(month: Int, year: Int) = repository.getTotalDurationByMonth(month, year)
}