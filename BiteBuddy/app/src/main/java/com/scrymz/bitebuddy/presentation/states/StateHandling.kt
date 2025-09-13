package com.scrymz.bitebuddy.presentation.states

import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.data.local.model.model.Food

data class CopyDatabaseState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class GetAllDataFromDatabaseState(
    val isLoading: Boolean = false,
    val data: List<Food> = emptyList(),
    val error: String = ""
)

data class SearchFoodState(
    val isLoading: Boolean = false,
    val data: List<Food> = emptyList(),
    val error: String = ""
)

data class UpsertFoodState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class DeleteFoodState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class GetAllFoodState(
    val isLoading: Boolean = false,
    val data: List<FoodTable> = emptyList(),
    val error: String = ""
)

data class GetByDateState(
    val isLoading: Boolean = false,
    val data: List<FoodTable> = emptyList(),
    val error: String = ""
)

data class GetByConsumedTimeState(
    val isLoading: Boolean = false,
    val data: List<FoodTable> = emptyList(),
    val error: String = ""
)

data class GetProteinState(
    val isLoading: Boolean = false,
    val value: Double = 0.0,
    val error: String = ""
)

data class GetCaloriesState(
    val isLoading: Boolean = false,
    val value: Double = 0.0,
    val error: String = ""
)

//men
data class UpsertPeriodState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class DeletePeriodState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class GetAllPeriodsDescendingState(
    val isLoading: Boolean = false,
    val data: List<MenstrualPeriod> = emptyList(),
    val error: String = ""
)

data class GetPeriodsByMonthState(
    val isLoading: Boolean = false,
    val data: List<MenstrualPeriod> = emptyList(),
    val error: String = ""
)

data class GetPeriodsByPainLevelState(
    val isLoading: Boolean = false,
    val data: List<MenstrualPeriod> = emptyList(),
    val error: String = ""
)

data class GetPeriodsByTimeOfDayState(
    val isLoading: Boolean = false,
    val data: List<MenstrualPeriod> = emptyList(),
    val error: String = ""
)

data class GetPeriodByIdState(
    val isLoading: Boolean = false,
    val data: MenstrualPeriod? = null,
    val error: String = ""
)

data class GetPeriodsByYearState(
    val isLoading: Boolean = false,
    val data: List<MenstrualPeriod> = emptyList(),
    val error: String = ""
)
