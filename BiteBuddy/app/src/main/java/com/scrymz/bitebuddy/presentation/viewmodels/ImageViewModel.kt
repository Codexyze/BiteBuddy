package com.scrymz.bitebuddy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.usecases.GetAllImageUseCase
import com.scrymz.bitebuddy.domain.usecases.GetImageFoldersUseCase
import com.scrymz.bitebuddy.domain.usecases.GetImagesFromFolderUseCase
import com.scrymz.bitebuddy.presentation.states.GetAllImageState
import com.scrymz.bitebuddy.presentation.states.GetImageFoldersState
import com.scrymz.bitebuddy.presentation.states.GetImagesFromFolderState
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
    private val getAllImageUseCase: GetAllImageUseCase,
    private val getImageFoldersUseCase: GetImageFoldersUseCase,
    private val getImagesFromFolderUseCase: GetImagesFromFolderUseCase
) : ViewModel(){
    private val _getAllImageState = MutableStateFlow(GetAllImageState())
    val getAllImageState =_getAllImageState.asStateFlow()

    private val _getImageFoldersState = MutableStateFlow(GetImageFoldersState())
    val getImageFoldersState = _getImageFoldersState.asStateFlow()

    private val _getImagesFromFolderState = MutableStateFlow(GetImagesFromFolderState())
    val getImagesFromFolderState = _getImagesFromFolderState.asStateFlow()

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

    fun getImageFolders(){
        viewModelScope.launch {
            getImageFoldersUseCase.invoke().flowOn(Dispatchers.IO).collect { result->
                withContext(Dispatchers.Main){
                    when(result){
                        is ResultState.loading -> {
                            _getImageFoldersState.value = GetImageFoldersState(isLoading = true)
                        }
                        is ResultState.Sucess->{
                            _getImageFoldersState.value = GetImageFoldersState(
                                isLoading = false ,
                                data = result.data
                            )
                        }
                        is ResultState.Error->{
                            _getImageFoldersState.value = GetImageFoldersState(
                                isLoading = false,
                                error = result.error
                            )
                        }
                    }
                }
            }
        }
    }

    fun getImagesFromFolder(folderName: String){
        viewModelScope.launch {
            getImagesFromFolderUseCase.invoke(folderName).flowOn(Dispatchers.IO).collect { result->
                withContext(Dispatchers.Main){
                    when(result){
                        is ResultState.loading -> {
                            _getImagesFromFolderState.value = GetImagesFromFolderState(isLoading = true)
                        }
                        is ResultState.Sucess->{
                            _getImagesFromFolderState.value = GetImagesFromFolderState(
                                isLoading = false ,
                                data = result.data
                            )
                        }
                        is ResultState.Error->{
                            _getImagesFromFolderState.value = GetImagesFromFolderState(
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