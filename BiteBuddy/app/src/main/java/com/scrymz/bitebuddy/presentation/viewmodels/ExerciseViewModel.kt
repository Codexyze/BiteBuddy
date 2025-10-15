package com.scrymz.bitebuddy.presentation.viewmodels



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.data.entity.Exercise
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
class ExerciseViewModel @Inject constructor(
    private val upsertExerciseUseCase: UpsertExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
    private val getAllExercisesDescendingUseCase: GetAllExercisesDescendingUseCase,
    private val getExercisesByDateUseCase: GetExercisesByDateUseCase,
    private val getExercisesByMonthUseCase: GetExercisesByMonthUseCase,
    private val getExercisesByTypeUseCase: GetExercisesByTypeUseCase,
    private val getExercisesByIntensityUseCase: GetExercisesByIntensityUseCase,
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    private val getExercisesByYearUseCase: GetExercisesByYearUseCase,
    private val getTotalCaloriesBurnedByDateUseCase: GetTotalCaloriesBurnedByDateUseCase,
    private val getTotalDurationByDateUseCase: GetTotalDurationByDateUseCase,
    private val getTotalCaloriesBurnedByMonthUseCase: GetTotalCaloriesBurnedByMonthUseCase,
    private val getTotalDurationByMonthUseCase: GetTotalDurationByMonthUseCase
) : ViewModel() {

    // STATES
    private val _upsertExerciseState = MutableStateFlow(UpsertExerciseState())
    val upsertExerciseState = _upsertExerciseState.asStateFlow()

    private val _deleteExerciseState = MutableStateFlow(DeleteExerciseState())
    val deleteExerciseState = _deleteExerciseState.asStateFlow()

    private val _allExercisesState = MutableStateFlow(GetAllExercisesDescendingState())
    val allExercisesState = _allExercisesState.asStateFlow()

    private val _exercisesByDateState = MutableStateFlow(GetExercisesByDateState())
    val exercisesByDateState = _exercisesByDateState.asStateFlow()

    private val _exercisesByMonthState = MutableStateFlow(GetExercisesByMonthState())
    val exercisesByMonthState = _exercisesByMonthState.asStateFlow()

    private val _exercisesByTypeState = MutableStateFlow(GetExercisesByTypeState())
    val exercisesByTypeState = _exercisesByTypeState.asStateFlow()

    private val _exercisesByIntensityState = MutableStateFlow(GetExercisesByIntensityState())
    val exercisesByIntensityState = _exercisesByIntensityState.asStateFlow()

    private val _exerciseByIdState = MutableStateFlow(GetExerciseByIdState())
    val exerciseByIdState = _exerciseByIdState.asStateFlow()

    private val _exercisesByYearState = MutableStateFlow(GetExercisesByYearState())
    val exercisesByYearState = _exercisesByYearState.asStateFlow()

    private val _totalCaloriesBurnedState = MutableStateFlow(GetTotalCaloriesBurnedState())
    val totalCaloriesBurnedState = _totalCaloriesBurnedState.asStateFlow()

    private val _totalDurationState = MutableStateFlow(GetTotalDurationState())
    val totalDurationState = _totalDurationState.asStateFlow()

    // FUNCTIONS

    fun upsertExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            upsertExerciseUseCase(exercise).collect { result ->
                _upsertExerciseState.value = when (result) {
                    is ResultState.loading -> UpsertExerciseState(isLoading = true)
                    is ResultState.Sucess -> UpsertExerciseState(message = result.data)
                    is ResultState.Error -> UpsertExerciseState(error = result.error)
                }
            }
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteExerciseUseCase(exercise).collect { result ->
                _deleteExerciseState.value = when (result) {
                    is ResultState.loading -> DeleteExerciseState(isLoading = true)
                    is ResultState.Sucess -> DeleteExerciseState(message = result.data)
                    is ResultState.Error -> DeleteExerciseState(error = result.error)
                }
            }
        }
    }

    fun getAllExercisesDescending() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllExercisesDescendingUseCase().collect { result ->
                _allExercisesState.value = when (result) {
                    is ResultState.loading -> GetAllExercisesDescendingState(isLoading = true)
                    is ResultState.Sucess -> GetAllExercisesDescendingState(data = result.data)
                    is ResultState.Error -> GetAllExercisesDescendingState(error = result.error)
                }
            }
        }
    }

    fun getExercisesByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getExercisesByDateUseCase(date).collect { result ->
                _exercisesByDateState.value = when (result) {
                    is ResultState.loading -> GetExercisesByDateState(isLoading = true)
                    is ResultState.Sucess -> GetExercisesByDateState(data = result.data)
                    is ResultState.Error -> GetExercisesByDateState(error = result.error)
                }
            }
        }
    }

    fun getExercisesByMonth(month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getExercisesByMonthUseCase(month, year).collect { result ->
                _exercisesByMonthState.value = when (result) {
                    is ResultState.loading -> GetExercisesByMonthState(isLoading = true)
                    is ResultState.Sucess -> GetExercisesByMonthState(data = result.data)
                    is ResultState.Error -> GetExercisesByMonthState(error = result.error)
                }
            }
        }
    }

    fun getExercisesByType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getExercisesByTypeUseCase(type).collect { result ->
                _exercisesByTypeState.value = when (result) {
                    is ResultState.loading -> GetExercisesByTypeState(isLoading = true)
                    is ResultState.Sucess -> GetExercisesByTypeState(data = result.data)
                    is ResultState.Error -> GetExercisesByTypeState(error = result.error)
                }
            }
        }
    }

    fun getExercisesByIntensity(intensity: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getExercisesByIntensityUseCase(intensity).collect { result ->
                _exercisesByIntensityState.value = when (result) {
                    is ResultState.loading -> GetExercisesByIntensityState(isLoading = true)
                    is ResultState.Sucess -> GetExercisesByIntensityState(data = result.data)
                    is ResultState.Error -> GetExercisesByIntensityState(error = result.error)
                }
            }
        }
    }

    fun getExerciseById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getExerciseByIdUseCase(id).collect { result ->
                _exerciseByIdState.value = when (result) {
                    is ResultState.loading -> GetExerciseByIdState(isLoading = true)
                    is ResultState.Sucess -> GetExerciseByIdState(data = result.data)
                    is ResultState.Error -> GetExerciseByIdState(error = result.error)
                }
            }
        }
    }

    fun getExercisesByYear(year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getExercisesByYearUseCase(year).collect { result ->
                _exercisesByYearState.value = when (result) {
                    is ResultState.loading -> GetExercisesByYearState(isLoading = true)
                    is ResultState.Sucess -> GetExercisesByYearState(data = result.data)
                    is ResultState.Error -> GetExercisesByYearState(error = result.error)
                }
            }
        }
    }

    fun getTotalCaloriesBurnedByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalCaloriesBurnedByDateUseCase(date).collect { result ->
                _totalCaloriesBurnedState.value = when (result) {
                    is ResultState.loading -> GetTotalCaloriesBurnedState(isLoading = true)
                    is ResultState.Sucess -> GetTotalCaloriesBurnedState(value = result.data)
                    is ResultState.Error -> GetTotalCaloriesBurnedState(error = result.error)
                }
            }
        }
    }

    fun getTotalDurationByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalDurationByDateUseCase(date).collect { result ->
                _totalDurationState.value = when (result) {
                    is ResultState.loading -> GetTotalDurationState(isLoading = true)
                    is ResultState.Sucess -> GetTotalDurationState(value = result.data)
                    is ResultState.Error -> GetTotalDurationState(error = result.error)
                }
            }
        }
    }

    fun getTotalCaloriesBurnedByMonth(month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalCaloriesBurnedByMonthUseCase(month, year).collect { result ->
                _totalCaloriesBurnedState.value = when (result) {
                    is ResultState.loading -> GetTotalCaloriesBurnedState(isLoading = true)
                    is ResultState.Sucess -> GetTotalCaloriesBurnedState(value = result.data)
                    is ResultState.Error -> GetTotalCaloriesBurnedState(error = result.error)
                }
            }
        }
    }

    fun getTotalDurationByMonth(month: Int, year: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTotalDurationByMonthUseCase(month, year).collect { result ->
                _totalDurationState.value = when (result) {
                    is ResultState.loading -> GetTotalDurationState(isLoading = true)
                    is ResultState.Sucess -> GetTotalDurationState(value = result.data)
                    is ResultState.Error -> GetTotalDurationState(error = result.error)
                }
            }
        }
    }
}
