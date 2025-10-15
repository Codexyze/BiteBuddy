package com.scrymz.bitebuddy.presentation.viewmodels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.data.entity.WaterIntake
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.usecases.*
import com.scrymz.bitebuddy.presentation.states.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterIntakeViewModel @Inject constructor(
    private val upsertWaterIntakeUseCase: UpsertWaterIntakeUseCase,
    private val deleteWaterIntakeUseCase: DeleteWaterIntakeUseCase,
    private val getAllWaterIntakesDescendingUseCase: GetAllWaterIntakesDescendingUseCase,
    private val getWaterIntakesByDateUseCase: GetWaterIntakesByDateUseCase,
    private val getWaterIntakesByMonthUseCase: GetWaterIntakesByMonthUseCase,
    private val getWaterIntakeByIdUseCase: GetWaterIntakeByIdUseCase,
    private val getWaterIntakesByYearUseCase: GetWaterIntakesByYearUseCase,
    private val getTotalWaterByDateUseCase: GetTotalWaterByDateUseCase,
    private val getTotalWaterByMonthUseCase: GetTotalWaterByMonthUseCase,
    private val getTotalWaterByYearUseCase: GetTotalWaterByYearUseCase,
    private val getAverageWaterByMonthUseCase: GetAverageWaterByMonthUseCase,
    private val deleteWaterIntakesByDateUseCase: DeleteWaterIntakesByDateUseCase
) : ViewModel() {

    // STATES
    private val _upsertWaterIntakeState = MutableStateFlow(UpsertWaterIntakeState())
    val upsertWaterIntakeState = _upsertWaterIntakeState.asStateFlow()

    private val _deleteWaterIntakeState = MutableStateFlow(DeleteWaterIntakeState())
    val deleteWaterIntakeState = _deleteWaterIntakeState.asStateFlow()

    private val _allWaterIntakesState = MutableStateFlow(GetAllWaterIntakesDescendingState())
    val allWaterIntakesState = _allWaterIntakesState.asStateFlow()

    private val _waterIntakesByDateState = MutableStateFlow(GetWaterIntakesByDateState())
    val waterIntakesByDateState = _waterIntakesByDateState.asStateFlow()

    private val _waterIntakesByMonthState = MutableStateFlow(GetWaterIntakesByMonthState())
    val waterIntakesByMonthState = _waterIntakesByMonthState.asStateFlow()

    private val _waterIntakeByIdState = MutableStateFlow(GetWaterIntakeByIdState())
    val waterIntakeByIdState = _waterIntakeByIdState.asStateFlow()

    private val _totalWaterState = MutableStateFlow(GetTotalWaterState())
    val totalWaterState = _totalWaterState.asStateFlow()

    private val _averageWaterState = MutableStateFlow(GetAverageWaterState())
    val averageWaterState = _averageWaterState.asStateFlow()

    private val _waterIntakesByYearState = MutableStateFlow(GetWaterIntakesByYearState())
    val waterIntakesByYearState = _waterIntakesByYearState.asStateFlow()


    // FUNCTIONS

    fun upsertWaterIntake(waterIntake: WaterIntake) {
        viewModelScope.launch(Dispatchers.IO) {
            upsertWaterIntakeUseCase(waterIntake).collect { result ->
                _upsertWaterIntakeState.value = when (result) {
                    is ResultState.loading -> UpsertWaterIntakeState(isLoading = true)
                    is ResultState.Sucess -> UpsertWaterIntakeState(message = result.data)
                    is ResultState.Error -> UpsertWaterIntakeState(error = result.error)
                }
            }
        }
    }

    fun deleteWaterIntake(waterIntake: WaterIntake) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteWaterIntakeUseCase(waterIntake).collect { result ->
                _deleteWaterIntakeState.value = when (result) {
                    is ResultState.loading -> DeleteWaterIntakeState(isLoading = true)
                    is ResultState.Sucess -> DeleteWaterIntakeState(message = result.data)
                    is ResultState.Error -> DeleteWaterIntakeState(error = result.error)
                }
            }
        }
    }

    fun getAllWaterIntakesDescending() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWaterIntakesDescendingUseCase().collect { result ->
                _allWaterIntakesState.value = when (result) {
                    is ResultState.loading -> GetAllWaterIntakesDescendingState(isLoading = true)
                    is ResultState.Sucess -> GetAllWaterIntakesDescendingState(data = result.data)
                    is ResultState.Error -> GetAllWaterIntakesDescendingState(error = result.error)
                }
            }
        }
    }

    fun getWaterIntakesByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getWaterIntakesByDateUseCase(date).collect { result ->
                _waterIntakesByDateState.value = when (result) {
                    is ResultState.loading -> GetWaterIntakesByDateState(isLoading = true)
                    is ResultState.Sucess -> GetWaterIntakesByDateState(data = result.data)
                    is ResultState.Error -> GetWaterIntakesByDateState(error = result.error)
                }
            }
        }
    }

    fun getWaterIntakesByMonth(month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getWaterIntakesByMonthUseCase(month, year).collect { result ->
                _waterIntakesByMonthState.value = when (result) {
                    is ResultState.loading -> GetWaterIntakesByMonthState(isLoading = true)
                    is ResultState.Sucess -> GetWaterIntakesByMonthState(data = result.data)
                    is ResultState.Error -> GetWaterIntakesByMonthState(error = result.error)
                }
            }
        }
    }

    fun getWaterIntakeById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getWaterIntakeByIdUseCase(id).collect { result ->
                _waterIntakeByIdState.value = when (result) {
                    is ResultState.loading -> GetWaterIntakeByIdState(isLoading = true)
                    is ResultState.Sucess -> GetWaterIntakeByIdState(data = result.data)
                    is ResultState.Error -> GetWaterIntakeByIdState(error = result.error)
                }
            }
        }
    }

    fun getTotalWaterByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalWaterByDateUseCase(date).collect { result ->
                _totalWaterState.value = when (result) {
                    is ResultState.loading -> GetTotalWaterState(isLoading = true)
                    is ResultState.Sucess -> GetTotalWaterState(value = result.data)
                    is ResultState.Error -> GetTotalWaterState(error = result.error)
                }
            }
        }
    }

    fun getTotalWaterByMonth(month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalWaterByMonthUseCase(month, year).collect { result ->
                _totalWaterState.value = when (result) {
                    is ResultState.loading -> GetTotalWaterState(isLoading = true)
                    is ResultState.Sucess -> GetTotalWaterState(value = result.data)
                    is ResultState.Error -> GetTotalWaterState(error = result.error)
                }
            }
        }
    }

    fun getTotalWaterByYear(year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalWaterByYearUseCase(year).collect { result ->
                _totalWaterState.value = when (result) {
                    is ResultState.loading -> GetTotalWaterState(isLoading = true)
                    is ResultState.Sucess -> GetTotalWaterState(value = result.data)
                    is ResultState.Error -> GetTotalWaterState(error = result.error)
                }
            }
        }
    }

    fun getAverageWaterByMonth(month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getAverageWaterByMonthUseCase(month, year).collect { result ->
                _averageWaterState.value = when (result) {
                    is ResultState.loading -> GetAverageWaterState(isLoading = true)
                    is ResultState.Sucess -> GetAverageWaterState(value = result.data)
                    is ResultState.Error -> GetAverageWaterState(error = result.error)
                }
            }
        }
    }

    fun deleteWaterIntakesByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteWaterIntakesByDateUseCase(date).collect { result ->
                _deleteWaterIntakeState.value = when (result) {
                    is ResultState.loading -> DeleteWaterIntakeState(isLoading = true)
                    is ResultState.Sucess -> DeleteWaterIntakeState(message = result.data)
                    is ResultState.Error -> DeleteWaterIntakeState(error = result.error)
                }
            }
        }
    }

    fun getWaterIntakesByYear(year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getWaterIntakesByYearUseCase(year).collect { result ->
                _waterIntakesByYearState.value = when (result) {
                    is ResultState.loading -> GetWaterIntakesByYearState(isLoading = true)
                    is ResultState.Sucess -> GetWaterIntakesByYearState(data = result.data)
                    is ResultState.Error -> GetWaterIntakesByYearState(error = result.error)
                }
            }
        }
    }

}
