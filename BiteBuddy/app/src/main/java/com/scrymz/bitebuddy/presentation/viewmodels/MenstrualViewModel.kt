package com.scrymz.bitebuddy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.data.entity.MenstrualPeriod
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.usecases.DeletePeriodUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllPeriodsDescendingUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodByIdUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByPainLevelUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByTimeOfDayUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByYearUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertPeriodUseCase
import com.scrymz.bitebuddy.presentation.states.DeletePeriodState
import com.scrymz.bitebuddy.presentation.states.GetAllPeriodsDescendingState
import com.scrymz.bitebuddy.presentation.states.GetPeriodByIdState
import com.scrymz.bitebuddy.presentation.states.GetPeriodsByMonthState
import com.scrymz.bitebuddy.presentation.states.GetPeriodsByPainLevelState
import com.scrymz.bitebuddy.presentation.states.GetPeriodsByTimeOfDayState
import com.scrymz.bitebuddy.presentation.states.GetPeriodsByYearState
import com.scrymz.bitebuddy.presentation.states.UpsertPeriodState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MenstrualViewModel @Inject constructor(
    private val upsertPeriodUseCase: UpsertPeriodUseCase,
    private val deletePeriodUseCase: DeletePeriodUseCase,
    private val getAllPeriodsDescendingUseCase: GetAllPeriodsDescendingUseCase,
    private val getPeriodsByMonthUseCase: GetPeriodsByMonthUseCase,
    private val getPeriodsByPainLevelUseCase: GetPeriodsByPainLevelUseCase,
    private val getPeriodsByTimeOfDayUseCase: GetPeriodsByTimeOfDayUseCase,
    private val getPeriodByIdUseCase: GetPeriodByIdUseCase,
    private val getPeriodsByYearUseCase: GetPeriodsByYearUseCase
) : ViewModel() {

    // STATES
    private val _upsertPeriodState = MutableStateFlow(UpsertPeriodState())
    val upsertPeriodState = _upsertPeriodState.asStateFlow()

    private val _deletePeriodState = MutableStateFlow(DeletePeriodState())
    val deletePeriodState = _deletePeriodState.asStateFlow()

    private val _allPeriodsState = MutableStateFlow(GetAllPeriodsDescendingState())
    val allPeriodsState = _allPeriodsState.asStateFlow()

    private val _periodsByMonthState = MutableStateFlow(GetPeriodsByMonthState())
    val periodsByMonthState = _periodsByMonthState.asStateFlow()

    private val _periodsByPainLevelState = MutableStateFlow(GetPeriodsByPainLevelState())
    val periodsByPainLevelState = _periodsByPainLevelState.asStateFlow()

    private val _periodsByTimeOfDayState = MutableStateFlow(GetPeriodsByTimeOfDayState())
    val periodsByTimeOfDayState = _periodsByTimeOfDayState.asStateFlow()

    private val _periodByIdState = MutableStateFlow(GetPeriodByIdState())
    val periodByIdState = _periodByIdState.asStateFlow()

    private val _periodsByYearState = MutableStateFlow(GetPeriodsByYearState())
    val periodsByYearState = _periodsByYearState.asStateFlow()

    // FUNCTIONS

    fun upsertPeriod(period: MenstrualPeriod) {
        viewModelScope.launch(Dispatchers.IO) {
            upsertPeriodUseCase(period).collect { result ->
                _upsertPeriodState.value = when (result) {
                    is ResultState.loading -> UpsertPeriodState(isLoading = true)
                    is ResultState.Sucess -> UpsertPeriodState(message = result.data)
                    is ResultState.Error -> UpsertPeriodState(error = result.error)
                }
            }
        }
    }

    fun deletePeriod(period: MenstrualPeriod) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePeriodUseCase(period).collect { result ->
                _deletePeriodState.value = when (result) {
                    is ResultState.loading -> DeletePeriodState(isLoading = true)
                    is ResultState.Sucess -> DeletePeriodState(message = result.data)
                    is ResultState.Error -> DeletePeriodState(error = result.error)
                }
            }
        }
    }

    fun getAllPeriodsDescending() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllPeriodsDescendingUseCase().collect { result ->
                _allPeriodsState.value = when (result) {
                    is ResultState.loading -> GetAllPeriodsDescendingState(isLoading = true)
                    is ResultState.Sucess -> GetAllPeriodsDescendingState(data = result.data)
                    is ResultState.Error -> GetAllPeriodsDescendingState(error = result.error)
                }
            }
        }
    }

    fun getPeriodsByMonth(month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPeriodsByMonthUseCase(month, year).collect { result ->
                _periodsByMonthState.value = when (result) {
                    is ResultState.loading -> GetPeriodsByMonthState(isLoading = true)
                    is ResultState.Sucess -> GetPeriodsByMonthState(data = result.data)
                    is ResultState.Error -> GetPeriodsByMonthState(error = result.error)
                }
            }
        }
    }

    fun getPeriodsByPainLevel(painLevel: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getPeriodsByPainLevelUseCase(painLevel).collect { result ->
                _periodsByPainLevelState.value = when (result) {
                    is ResultState.loading -> GetPeriodsByPainLevelState(isLoading = true)
                    is ResultState.Sucess -> GetPeriodsByPainLevelState(data = result.data)
                    is ResultState.Error -> GetPeriodsByPainLevelState(error = result.error)
                }
            }
        }
    }

    fun getPeriodsByTimeOfDay(timeOfDay: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getPeriodsByTimeOfDayUseCase(timeOfDay).collect { result ->
                _periodsByTimeOfDayState.value = when (result) {
                    is ResultState.loading -> GetPeriodsByTimeOfDayState(isLoading = true)
                    is ResultState.Sucess -> GetPeriodsByTimeOfDayState(data = result.data)
                    is ResultState.Error -> GetPeriodsByTimeOfDayState(error = result.error)
                }
            }
        }
    }

    fun getPeriodById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getPeriodByIdUseCase(id).collect { result ->
                _periodByIdState.value = when (result) {
                    is ResultState.loading -> GetPeriodByIdState(isLoading = true)
                    is ResultState.Sucess -> GetPeriodByIdState(data = result.data)
                    is ResultState.Error -> GetPeriodByIdState(error = result.error)
                }
            }
        }
    }

    fun getPeriodsByYear(year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPeriodsByYearUseCase(year).collect { result ->
                _periodsByYearState.value = when (result) {
                    is ResultState.loading -> GetPeriodsByYearState(isLoading = true)
                    is ResultState.Sucess -> GetPeriodsByYearState(data = result.data)
                    is ResultState.Error -> GetPeriodsByYearState(error = result.error)
                }
            }
        }
    }
}
