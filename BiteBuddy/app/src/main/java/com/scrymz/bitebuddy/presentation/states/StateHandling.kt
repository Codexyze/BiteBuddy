package com.scrymz.bitebuddy.presentation.states

import com.scrymz.bitebuddy.data.entity.Exercise
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.data.entity.WaterIntake
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


//Water intake states
// Exercise States
data class UpsertExerciseState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class DeleteExerciseState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class GetAllExercisesDescendingState(
    val isLoading: Boolean = false,
    val data: List<Exercise> = emptyList(),
    val error: String = ""
)

data class GetExercisesByDateState(
    val isLoading: Boolean = false,
    val data: List<Exercise> = emptyList(),
    val error: String = ""
)

data class GetExercisesByMonthState(
    val isLoading: Boolean = false,
    val data: List<Exercise> = emptyList(),
    val error: String = ""
)

data class GetExercisesByTypeState(
    val isLoading: Boolean = false,
    val data: List<Exercise> = emptyList(),
    val error: String = ""
)

data class GetExercisesByIntensityState(
    val isLoading: Boolean = false,
    val data: List<Exercise> = emptyList(),
    val error: String = ""
)

data class GetExerciseByIdState(
    val isLoading: Boolean = false,
    val data: Exercise? = null,
    val error: String = ""
)

data class GetExercisesByYearState(
    val isLoading: Boolean = false,
    val data: List<Exercise> = emptyList(),
    val error: String = ""
)

data class GetTotalCaloriesBurnedState(
    val isLoading: Boolean = false,
    val value: Double = 0.0,
    val error: String = ""
)

data class GetTotalDurationState(
    val isLoading: Boolean = false,
    val value: Int = 0,
    val error: String = ""
)

// Water Intake States
data class UpsertWaterIntakeState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class DeleteWaterIntakeState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)

data class GetAllWaterIntakesDescendingState(
    val isLoading: Boolean = false,
    val data: List<WaterIntake> = emptyList(),
    val error: String = ""
)

data class GetWaterIntakesByDateState(
    val isLoading: Boolean = false,
    val data: List<WaterIntake> = emptyList(),
    val error: String = ""
)

data class GetWaterIntakesByMonthState(
    val isLoading: Boolean = false,
    val data: List<WaterIntake> = emptyList(),
    val error: String = ""
)

data class GetWaterIntakeByIdState(
    val isLoading: Boolean = false,
    val data: WaterIntake? = null,
    val error: String = ""
)

data class GetTotalWaterState(
    val isLoading: Boolean = false,
    val value: Double = 0.0,
    val error: String = ""
)

data class GetAverageWaterState(
    val isLoading: Boolean = false,
    val value: Double = 0.0,
    val error: String = ""
)
data class GetWaterIntakesByYearState(
    val isLoading: Boolean = false,
    val data: List<WaterIntake> = emptyList(),
    val error: String = ""
)
