package com.scrymz.bitebuddy.presentation.states

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