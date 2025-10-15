package com.scrymz.bitebuddy.data.repoImpl


import com.scrymz.bitebuddy.data.dao.WaterIntakeDao
import com.scrymz.bitebuddy.data.entity.WaterIntake
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WaterIntakeRepositoryImpl @Inject constructor(
    private val dao: WaterIntakeDao
) : WaterIntakeRepository {

    override fun upsertWaterIntake(waterIntake: WaterIntake): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            if (waterIntake.id == 0L) {
                dao.insertWaterIntake(waterIntake)
                emit(ResultState.Sucess("Water intake added successfully"))
            } else {
                dao.updateWaterIntake(waterIntake)
                emit(ResultState.Sucess("Water intake updated successfully"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun deleteWaterIntake(waterIntake: WaterIntake): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.deleteWaterIntake(waterIntake)
            emit(ResultState.Sucess("Water intake deleted successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getAllWaterIntakesDescending(): Flow<ResultState<List<WaterIntake>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getAllWaterIntakesDescending().collect { intakes ->
                emit(ResultState.Sucess(intakes))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getWaterIntakesByDate(date: String): Flow<ResultState<List<WaterIntake>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getWaterIntakesByDate(date).collect { intakes ->
                emit(ResultState.Sucess(intakes))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getWaterIntakesByMonth(month: Int, year: Int): Flow<ResultState<List<WaterIntake>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getWaterIntakesByMonth(month, year).collect { intakes ->
                emit(ResultState.Sucess(intakes))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getWaterIntakeById(id: Long): Flow<ResultState<WaterIntake?>> = flow {
        emit(ResultState.loading)
        try {
            val intake = dao.getWaterIntakeById(id)
            emit(ResultState.Sucess(intake))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getWaterIntakesByYear(year: Int): Flow<ResultState<List<WaterIntake>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getWaterIntakesByYear(year).collect { intakes ->
                emit(ResultState.Sucess(intakes))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getTotalWaterByDate(date: String): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            val total = dao.getTotalWaterByDate(date) ?: 0.0
            emit(ResultState.Sucess(total))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getTotalWaterByMonth(month: Int, year: Int): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            val total = dao.getTotalWaterByMonth(month, year) ?: 0.0
            emit(ResultState.Sucess(total))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getTotalWaterByYear(year: Int): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            val total = dao.getTotalWaterByYear(year) ?: 0.0
            emit(ResultState.Sucess(total))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getAverageWaterByMonth(month: Int, year: Int): Flow<ResultState<Double>> = flow {
        emit(ResultState.loading)
        try {
            val average = dao.getAverageWaterByMonth(month, year) ?: 0.0
            emit(ResultState.Sucess(average))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun deleteWaterIntakesByDate(date: String): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.deleteWaterIntakesByDate(date)
            emit(ResultState.Sucess("Water intakes deleted successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }
}
