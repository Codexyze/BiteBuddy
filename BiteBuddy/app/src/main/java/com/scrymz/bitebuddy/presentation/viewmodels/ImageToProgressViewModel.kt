package com.scrymz.bitebuddy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.data.entity.ImageToProgress
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.usecases.DeleteImageToProgressUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllImageToProgressDescendingUseCase
import com.scrymz.bitebuddy.domain.usecases.GetImageToProgressByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetImageToProgressByIdUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertImageToProgressUseCase
import com.scrymz.bitebuddy.presentation.states.DeleteImageToProgressState
import com.scrymz.bitebuddy.presentation.states.GetAllImageToProgressDescendingState
import com.scrymz.bitebuddy.presentation.states.GetImageToProgressByDateState
import com.scrymz.bitebuddy.presentation.states.GetImageToProgressByIdState
import com.scrymz.bitebuddy.presentation.states.UpsertImageToProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageToProgressViewModel @Inject constructor(
    private val upsertImageToProgressUseCase: UpsertImageToProgressUseCase,
    private val deleteImageToProgressUseCase: DeleteImageToProgressUseCase,
    private val getAllImageToProgressDescendingUseCase: GetAllImageToProgressDescendingUseCase,
    private val getImageToProgressByDateUseCase: GetImageToProgressByDateUseCase,
    private val getImageToProgressByIdUseCase: GetImageToProgressByIdUseCase
) : ViewModel() {

    // STATES
    private val _upsertImageToProgressState = MutableStateFlow(UpsertImageToProgressState())
    val upsertImageToProgressState = _upsertImageToProgressState.asStateFlow()

    private val _deleteImageToProgressState = MutableStateFlow(DeleteImageToProgressState())
    val deleteImageToProgressState = _deleteImageToProgressState.asStateFlow()

    private val _allImageToProgressState = MutableStateFlow(GetAllImageToProgressDescendingState())
    val allImageToProgressState = _allImageToProgressState.asStateFlow()

    private val _imageToProgressByDateState = MutableStateFlow(GetImageToProgressByDateState())
    val imageToProgressByDateState = _imageToProgressByDateState.asStateFlow()

    private val _imageToProgressByIdState = MutableStateFlow(GetImageToProgressByIdState())
    val imageToProgressByIdState = _imageToProgressByIdState.asStateFlow()

    // FUNCTIONS

    fun upsertImageToProgress(imageToProgress: ImageToProgress) {
        viewModelScope.launch(Dispatchers.IO) {
            upsertImageToProgressUseCase(imageToProgress).collect { result ->
                _upsertImageToProgressState.value = when (result) {
                    is ResultState.loading -> UpsertImageToProgressState(isLoading = true)
                    is ResultState.Sucess -> UpsertImageToProgressState(message = result.data)
                    is ResultState.Error -> UpsertImageToProgressState(error = result.error)
                }
            }
        }
    }

    fun deleteImageToProgress(imageToProgress: ImageToProgress) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteImageToProgressUseCase(imageToProgress).collect { result ->
                _deleteImageToProgressState.value = when (result) {
                    is ResultState.loading -> DeleteImageToProgressState(isLoading = true)
                    is ResultState.Sucess -> DeleteImageToProgressState(message = result.data)
                    is ResultState.Error -> DeleteImageToProgressState(error = result.error)
                }
            }
        }
    }

    fun getAllImageToProgressDescending() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllImageToProgressDescendingUseCase().collect { result ->
                _allImageToProgressState.value = when (result) {
                    is ResultState.loading -> GetAllImageToProgressDescendingState(isLoading = true)
                    is ResultState.Sucess -> GetAllImageToProgressDescendingState(data = result.data)
                    is ResultState.Error -> GetAllImageToProgressDescendingState(error = result.error)
                }
            }
        }
    }

    fun getImageToProgressByDate(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getImageToProgressByDateUseCase(date).collect { result ->
                _imageToProgressByDateState.value = when (result) {
                    is ResultState.loading -> GetImageToProgressByDateState(isLoading = true)
                    is ResultState.Sucess -> GetImageToProgressByDateState(data = result.data)
                    is ResultState.Error -> GetImageToProgressByDateState(error = result.error)
                }
            }
        }
    }

    fun getImageToProgressById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getImageToProgressByIdUseCase(id).collect { result ->
                _imageToProgressByIdState.value = when (result) {
                    is ResultState.loading -> GetImageToProgressByIdState(isLoading = true)
                    is ResultState.Sucess -> GetImageToProgressByIdState(data = result.data)
                    is ResultState.Error -> GetImageToProgressByIdState(error = result.error)
                }
            }
        }
    }

    // Reset states
    fun resetUpsertState() {
        _upsertImageToProgressState.value = UpsertImageToProgressState()
    }

    fun resetDeleteState() {
        _deleteImageToProgressState.value = DeleteImageToProgressState()
    }
}

