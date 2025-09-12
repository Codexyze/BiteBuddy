package com.scrymz.bitebuddy.domain.StateHandeling

sealed class ResultState<out T>{
    object loading: ResultState<Nothing>()
    data class Sucess<T>(val data:T): ResultState<T>()
    data class Error(val error: String): ResultState<Nothing>()
}
