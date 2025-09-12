package com.scrymz.bitebuddy.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrymz.bitebuddy.core.DataBaseOpenHelper
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.presentation.states.CopyDatabaseState
import com.scrymz.bitebuddy.presentation.states.GetAllDataFromDatabaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
//
//@HiltViewModel
//class DatabaseOpnerViewModel @Inject constructor(
//    private val dataBaseOpenHelper: DataBaseOpenHelper
//) : ViewModel() {
//    private val _copyDatabase = MutableStateFlow(CopyDatabaseState())
//    val copyDatabase = _copyDatabase.asStateFlow()
//    private val _getAllDataFromDatabase = MutableStateFlow(GetAllDataFromDatabaseState())
//    val getAllDataFromDatabase = _getAllDataFromDatabase.asStateFlow()
//
//    fun copyDatabase() {
//      viewModelScope.launch(Dispatchers.IO) {
//          withContext(Dispatchers.Main){
//              dataBaseOpenHelper.copyDatabase().collect{resultState->
//                  when(resultState){
//                      is ResultState.loading->{
//                          _copyDatabase.value = CopyDatabaseState(isLoading = true)
//                      }
//                      is ResultState.Sucess->{
//                          _copyDatabase.value = CopyDatabaseState(message = resultState.data)
//                      }
//                      is ResultState.Error->{
//                          _copyDatabase.value = CopyDatabaseState(error = resultState.error)
//                      }
//                  }
//
//              }
//
//          }
//      }
//
//    }
//    fun getAllDataFromDatabase(){
//        viewModelScope.launch(Dispatchers.IO) {
//
//                dataBaseOpenHelper.getAllDataFromDatabase().collect{resultState->
//                    when(resultState){
//                        is ResultState.loading->{
//                            _getAllDataFromDatabase.value = GetAllDataFromDatabaseState(isLoading = true)
//                        }
//                        is ResultState.Sucess->{
//                            _getAllDataFromDatabase.value = GetAllDataFromDatabaseState(data = resultState.data)
//                        }
//                        is ResultState.Error->{
//                            _getAllDataFromDatabase.value = GetAllDataFromDatabaseState(error = resultState.error)
//                        }
//                    }
//
//                }
//
//
//        }
//    }
//
//
//}
@HiltViewModel
class DatabaseOpnerViewModel @Inject constructor(
    private val dataBaseOpenHelper: DataBaseOpenHelper
) : ViewModel() {

    private val _copyDatabase = MutableStateFlow(CopyDatabaseState())
    val copyDatabase = _copyDatabase.asStateFlow()

    private val _getAllDataFromDatabase = MutableStateFlow(GetAllDataFromDatabaseState())
    val getAllDataFromDatabase = _getAllDataFromDatabase.asStateFlow()

    fun copyDatabase() {
        Log.d("VM", "copyDatabase() called")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("VM", "copyDatabase() launched in IO dispatcher")
            withContext(Dispatchers.Main) {
                Log.d("VM", "copyDatabase() switched to Main thread")
                dataBaseOpenHelper.copyDatabase().collect { resultState ->
                    Log.d("VM", "copyDatabase() flow emitted: $resultState")
                    when (resultState) {
                        is ResultState.loading -> {
                            Log.d("VM", "DB Copy: Loading...")
                            _copyDatabase.value = CopyDatabaseState(isLoading = true)
                        }
                        is ResultState.Sucess -> {
                            Log.d("VM", "DB Copy: Success -> ${resultState.data}")
                            _copyDatabase.value = CopyDatabaseState(message = resultState.data)
                        }
                        is ResultState.Error -> {
                            Log.e("VM", "DB Copy: Error -> ${resultState.error}")
                            _copyDatabase.value = CopyDatabaseState(error = resultState.error)
                        }
                    }
                }
            }
        }
    }

    fun getAllDataFromDatabase() {
        Log.d("VM", "getAllDataFromDatabase() called")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("VM", "getAllDataFromDatabase() launched in IO dispatcher")
            dataBaseOpenHelper.getAllDataFromDatabase().collect { resultState ->
                Log.d("VM", "getAllDataFromDatabase() flow emitted: $resultState")
                when (resultState) {
                    is ResultState.loading -> {
                        Log.d("VM", "Fetching data: Loading...")
                        _getAllDataFromDatabase.value = GetAllDataFromDatabaseState(isLoading = true)
                    }
                    is ResultState.Sucess -> {
                        Log.d("VM", "Fetching data: Success -> ${resultState.data.size} items found")
                        _getAllDataFromDatabase.value = GetAllDataFromDatabaseState(data = resultState.data)
                    }
                    is ResultState.Error -> {
                        Log.e("VM", "Fetching data: Error -> ${resultState.error}")
                        _getAllDataFromDatabase.value = GetAllDataFromDatabaseState(error = resultState.error)
                    }
                }
            }
        }
    }
}
