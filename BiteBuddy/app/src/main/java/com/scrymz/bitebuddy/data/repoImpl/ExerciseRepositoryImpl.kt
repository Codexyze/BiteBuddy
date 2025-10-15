package com.scrymz.bitebuddy.data.repoImpl

import com.scrymz.bitebuddy.data.dao.ExerciseDao
import com.scrymz.bitebuddy.data.entity.Exercise
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val dao: ExerciseDao
) : ExerciseRepository {

    override fun upsertExercise(exercise: Exercise): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            if (exercise.id == 0L) {
                dao.insertExercise(exercise)
                emit(ResultState.Sucess("Exercise added successfully"))
            } else {
                dao.updateExercise(exercise)
                emit(ResultState.Sucess("Exercise updated successfully"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun deleteExercise(exercise: Exercise): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.deleteExercise(exercise)
            emit(ResultState.Sucess("Exercise deleted successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getAllExercisesDescending(): Flow<ResultState<List<Exercise>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getAllExercisesDescending().collect { exercises ->
                emit(ResultState.Sucess(exercises))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getExercisesByDate(date: String): Flow<ResultState<List<Exercise>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getExercisesByDate(date).collect { exercises ->
                emit(ResultState.Sucess(exercises))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getExercisesByMonth(month: Int, year: Int): Flow<ResultState<List<Exercise>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getExercisesByMonth(month, year).collect { exercises ->
                emit(ResultState.Sucess(exercises))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getExercisesByType(type: String): Flow<ResultState<List<Exercise>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getExercisesByType(type).collect { exercises ->
                emit(ResultState.Sucess(exercises))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getExercisesByIntensity(intensity: String): Flow<ResultState<List<Exercise>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getExercisesByIntensity(intensity).collect { exercises ->
                emit(ResultState.Sucess(exercises))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getExerciseById(id: Long): Flow<ResultState<Exercise?>> = flow {
        emit(ResultState.loading)
        try {
            val exercise = dao.getExerciseById(id)
            emit(ResultState.Sucess(exercise))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getExercisesByYear(year: Int): Flow<ResultState<List<Exercise>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getExercisesByYear(year).collect { exercises ->
                emit(ResultState.Sucess(exercises))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getTotalCaloriesBurnedByDate(date: String): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            val total = dao.getTotalCaloriesBurnedByDate(date) ?: 0.0
            emit(ResultState.Sucess(total))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getTotalDurationByDate(date: String): Flow<ResultState<Int>> = flow {
        emit(ResultState.loading)
        try {
            val total = dao.getTotalDurationByDate(date) ?: 0
            emit(ResultState.Sucess(total))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getTotalCaloriesBurnedByMonth(month: Int, year: Int): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            val total = dao.getTotalCaloriesBurnedByMonth(month, year) ?: 0.0
            emit(ResultState.Sucess(total))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getTotalDurationByMonth(month: Int, year: Int): Flow<ResultState<Int>> = flow {
        emit(ResultState.loading)
        try {
            val total = dao.getTotalDurationByMonth(month, year) ?: 0
            emit(ResultState.Sucess(total))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }
}
