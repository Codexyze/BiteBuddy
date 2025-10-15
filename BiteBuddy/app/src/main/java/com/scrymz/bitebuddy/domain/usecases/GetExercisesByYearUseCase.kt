package com.scrymz.bitebuddy.domain.usecases


import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesByYearUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(year: Int) = repository.getExercisesByYear(year)
}