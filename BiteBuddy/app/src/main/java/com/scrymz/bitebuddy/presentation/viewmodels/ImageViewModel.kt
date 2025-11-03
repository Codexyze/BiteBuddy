package com.scrymz.bitebuddy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.usecases.GetAllImageUseCase
import com.scrymz.bitebuddy.presentation.states.GetAllImageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getAllImageUseCase: GetAllImageUseCase
) : ViewModel(){
    private val _getAllImageState = MutableStateFlow(GetAllImageState())
    val getAllImageState =_getAllImageState.asStateFlow()

    fun getAllImage(){
        viewModelScope.launch {
            getAllImageUseCase.invoke().flowOn(Dispatchers.IO).collect { result->
                withContext(Dispatchers.Main){
                    when(result){

                        is ResultState.loading -> {
                            _getAllImageState.value = GetAllImageState(isLoading = false)
                        }
                        is ResultState.Sucess->{
                            _getAllImageState.value = GetAllImageState(
                                isLoading = false ,
                                data = result.data
                            )
                        }
                        is ResultState.Error->{
                            _getAllImageState.value = GetAllImageState(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }

                }

            }

        }
    }





}