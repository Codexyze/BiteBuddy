package com.scrymz.bitebuddy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.data.entity.FoodTable
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.usecases.DeleteFoodUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllFoodUseCase
import com.scrymz.bitebuddy.domain.usecases.GetByConsumedTimeUseCase
import com.scrymz.bitebuddy.domain.usecases.GetByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetCaloriesUseCase
import com.scrymz.bitebuddy.domain.usecases.GetProteinUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertFoodUseCase
import com.scrymz.bitebuddy.presentation.states.DeleteFoodState
import com.scrymz.bitebuddy.presentation.states.GetAllFoodState
import com.scrymz.bitebuddy.presentation.states.GetByConsumedTimeState
import com.scrymz.bitebuddy.presentation.states.GetByDateState
import com.scrymz.bitebuddy.presentation.states.GetCaloriesState
import com.scrymz.bitebuddy.presentation.states.GetProteinState
import com.scrymz.bitebuddy.presentation.states.UpsertFoodState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodViewModel @Inject constructor(
    private val upsertFoodUseCase: UpsertFoodUseCase,
    private val deleteFoodUseCase: DeleteFoodUseCase,
    private val getAllFoodUseCase: GetAllFoodUseCase,
    private val getByDateUseCase: GetByDateUseCase,
    private val getByConsumedTimeUseCase: GetByConsumedTimeUseCase,
    private val getProteinUseCase: GetProteinUseCase,
    private val getCaloriesUseCase: GetCaloriesUseCase
) : ViewModel() {

    // ---------- STATE FLOWS ----------
    private val _upsertFoodState = MutableStateFlow(UpsertFoodState())
    val upsertFoodState = _upsertFoodState.asStateFlow()

    private val _deleteFoodState = MutableStateFlow(DeleteFoodState())
    val deleteFoodState = _deleteFoodState.asStateFlow()

    private val _getAllFoodState = MutableStateFlow(GetAllFoodState())
    val getAllFoodState = _getAllFoodState.asStateFlow()

    private val _getByDateState = MutableStateFlow(GetByDateState())
    val getByDateState = _getByDateState.asStateFlow()

    private val _getByConsumedTimeState = MutableStateFlow(GetByConsumedTimeState())
    val getByConsumedTimeState = _getByConsumedTimeState.asStateFlow()

    private val _getProteinState = MutableStateFlow(GetProteinState())
    val getProteinState = _getProteinState.asStateFlow()

    private val _getCaloriesState = MutableStateFlow(GetCaloriesState())
    val getCaloriesState = _getCaloriesState.asStateFlow()

    // ---------- FUNCTIONS ----------
    fun upsertFood(food: FoodTable) {
        viewModelScope.launch(Dispatchers.IO) {
            upsertFoodUseCase(food).collect { result ->
                when (result) {
                    is ResultState.loading -> _upsertFoodState.value = UpsertFoodState(isLoading = true)
                    is ResultState.Sucess -> _upsertFoodState.value = UpsertFoodState(message = result.data)
                    is ResultState.Error -> _upsertFoodState.value = UpsertFoodState(error = result.error)
                }
            }
        }
    }

    fun deleteFood(food: FoodTable) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFoodUseCase(food).collect { result ->
                when (result) {
                    is ResultState.loading -> _deleteFoodState.value = DeleteFoodState(isLoading = true)
                    is ResultState.Sucess -> _deleteFoodState.value = DeleteFoodState(message = result.data)
                    is ResultState.Error -> _deleteFoodState.value = DeleteFoodState(error = result.error)
                }
            }
        }
    }

    fun getAllFood() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFoodUseCase().collect { result ->
                when (result) {
                    is ResultState.loading -> _getAllFoodState.value = GetAllFoodState(isLoading = true)
                    is ResultState.Sucess -> _getAllFoodState.value = GetAllFoodState(data = result.data)
                    is ResultState.Error -> _getAllFoodState.value = GetAllFoodState(error = result.error)
                }
            }
        }
    }

    fun getByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getByDateUseCase(date).collect { result ->
                when (result) {
                    is ResultState.loading -> _getByDateState.value = GetByDateState(isLoading = true)
                    is ResultState.Sucess -> _getByDateState.value = GetByDateState(data = result.data)
                    is ResultState.Error -> _getByDateState.value = GetByDateState(error = result.error)
                }
            }
        }
    }

    fun getByConsumedTime(time: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getByConsumedTimeUseCase(time).collect { result ->
                when (result) {
                    is ResultState.loading -> _getByConsumedTimeState.value = GetByConsumedTimeState(isLoading = true)
                    is ResultState.Sucess -> _getByConsumedTimeState.value = GetByConsumedTimeState(data = result.data)
                    is ResultState.Error -> _getByConsumedTimeState.value = GetByConsumedTimeState(error = result.error)
                }
            }
        }
    }

    fun getProtein(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getProteinUseCase(date = date).collect { result ->
                when (result) {
                    is ResultState.loading -> _getProteinState.value = GetProteinState(isLoading = true)
                    is ResultState.Sucess -> _getProteinState.value = GetProteinState(value = result.data)
                    is ResultState.Error -> _getProteinState.value = GetProteinState(error = result.error)
                }
            }
        }
    }

    fun getCalories(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCaloriesUseCase(date =date ).collect { result ->
                when (result) {
                    is ResultState.loading -> _getCaloriesState.value = GetCaloriesState(isLoading = true)
                    is ResultState.Sucess -> _getCaloriesState.value = GetCaloriesState(value = result.data)
                    is ResultState.Error -> _getCaloriesState.value = GetCaloriesState(error = result.error)
                }
            }
        }
    }
}
