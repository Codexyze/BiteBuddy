package com.scrymz.bitebuddy.data.repoImpl

import com.scrymz.bitebuddy.data.dao.MenstrualDao
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MenstrualRepositoryImpl @Inject constructor(
    private val dao: MenstrualDao
) : MenstrualRepository {

    override fun upsertPeriod(period: MenstrualPeriod): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            if (period.id == 0L) {
                dao.insertPeriod(period)
                emit(ResultState.Sucess("Period added successfully"))
            } else {
                dao.updatePeriod(period)
                emit(ResultState.Sucess("Period updated successfully"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun deletePeriod(period: MenstrualPeriod): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.deletePeriod(period)
            emit(ResultState.Sucess("Period deleted successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getAllPeriodsDescending(): Flow<ResultState<List<MenstrualPeriod>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getAllPeriodsDescending().collect { periods ->
                emit(ResultState.Sucess(periods))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getPeriodsByMonth(month: Int, year: Int): Flow<ResultState<List<MenstrualPeriod>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getPeriodsByMonth(month, year).collect { periods ->
                emit(ResultState.Sucess(periods))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getPeriodsByPainLevel(painLevel: String): Flow<ResultState<List<MenstrualPeriod>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getPeriodsByPainLevel(painLevel).collect { periods ->
                emit(ResultState.Sucess(periods))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getPeriodsByTimeOfDay(timeOfDay: String): Flow<ResultState<List<MenstrualPeriod>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getPeriodsByTimeOfDay(timeOfDay).collect { periods ->
                emit(ResultState.Sucess(periods))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getPeriodById(id: Long): Flow<ResultState<MenstrualPeriod?>> = flow {
        emit(ResultState.loading)
        try {
            val period = dao.getPeriodById(id)
            emit(ResultState.Sucess(period))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getPeriodsByYear(year: Int): Flow<ResultState<List<MenstrualPeriod>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getPeriodsByYear(year).collect { periods ->
                emit(ResultState.Sucess(periods))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }
}