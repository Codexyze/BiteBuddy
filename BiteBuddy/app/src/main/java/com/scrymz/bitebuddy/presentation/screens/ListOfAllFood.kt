package com.scrymz.bitebuddy.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scrymz.bitebuddy.data.local.Food
import com.scrymz.bitebuddy.presentation.viewmodels.DatabaseOpnerViewModel

//
//@Composable
//fun ListOfAllFood(
//    databaseOpenerViewModel: DatabaseOpnerViewModel = hiltViewModel()
//) {
//    val copyDatabaseState by databaseOpenerViewModel.copyDatabase.collectAsState()
//    val allFoodState by databaseOpenerViewModel.getAllDataFromDatabase.collectAsState()
//    val searchState by databaseOpenerViewModel.searchFoodState.collectAsState()
//
//    var searchQuery by remember { mutableStateOf("") }
//
//    // ✅ Start DB copy
//    LaunchedEffect(Unit) {
//        databaseOpenerViewModel.copyDatabase()
//    }
//
//    // ✅ Trigger data load when copy is done
//    LaunchedEffect(copyDatabaseState) {
//        if (!copyDatabaseState.isLoading && copyDatabaseState.error.isEmpty()) {
//            databaseOpenerViewModel.getAllDataFromDatabase()
//        }
//    }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//
//        // 🔍 Search bar
//        OutlinedTextField(
//            value = searchQuery,
//            onValueChange = {
//                searchQuery = it
//                databaseOpenerViewModel.searchFood(it)
//            },
//            label = { Text("Search Food...") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            singleLine = true
//        )
//
//        Box(modifier = Modifier.fillMaxSize()) {
//            when {
//                copyDatabaseState.isLoading || allFoodState.isLoading || searchState.isLoading -> {
//                    CircularProgressIndicator(Modifier.align(Alignment.Center))
//                }
//
//                copyDatabaseState.error.isNotEmpty() -> {
//                    Text(
//                        text = copyDatabaseState.error,
//                        color = Color.Red,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//
//                allFoodState.error.isNotEmpty() -> {
//                    Text(
//                        text = allFoodState.error,
//                        color = Color.Red,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//
//                // ✅ Show search results if user typed something
//                searchQuery.isNotBlank() -> {
//                    if (searchState.data.isNotEmpty()) {
//                        LazyColumn(
//                            modifier = Modifier.fillMaxSize(),
//                            contentPadding = PaddingValues(16.dp),
//                            verticalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            items(searchState.data) { food -> FoodCard(food) }
//                        }
//                    } else {
//                        Text(
//                            text = "No matching food found",
//                            color = Color.Gray,
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier.align(Alignment.Center)
//                        )
//                    }
//                }
//
//                // ✅ Otherwise show all food
//                allFoodState.data.isNotEmpty() -> {
//                    LazyColumn(
//                        modifier = Modifier.fillMaxSize(),
//                        contentPadding = PaddingValues(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        items(allFoodState.data) { food -> FoodCard(food) }
//                    }
//                }
//
//                else -> {
//                    Text(
//                        text = "No Food Data Found",
//                        color = Color.Gray,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            }
//        }
//    }
//}
//@Composable
//fun FoodCard(food: Food) {
//    Card(
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                // ✅ In future: navigate to a detail screen with food.id
//            }
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = food.foodname,
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(text = "Type: ${food.type ?: "Unknown"}")
//            Text(text = "Calories: ${food.calories ?: "N/A"}")
//            Text(text = "Protein: ${food.protein ?: "N/A"} g")
//        }
//    }
//}

@Composable
fun FoodCard(food: Food) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // ✅ Matches theme surface color
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // ✅ In future: navigate to a detail screen with food.id
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 🍽 Food Name — Primary Color (Green)
            Text(
                text = food.foodname,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 🏷 Food Type — Secondary Color (Lighter Green)
            Text(
                text = "Type: ${food.type ?: "Unknown"}",
                color = MaterialTheme.colorScheme.secondary
            )

            // 🔥 Calories — Tertiary Color (Orange Accent)
            Text(
                text = "Calories: ${food.calories ?: "N/A"}",
                color = MaterialTheme.colorScheme.tertiary
            )

            // 🥩 Protein — Keep neutral but slightly dimmed
            Text(
                text = "Protein: ${food.protein ?: "N/A"} g",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun ListOfAllFood(
    databaseOpenerViewModel: DatabaseOpnerViewModel = hiltViewModel()
) {
    val copyDatabaseState by databaseOpenerViewModel.copyDatabase.collectAsState()
    val allFoodState by databaseOpenerViewModel.getAllDataFromDatabase.collectAsState()
    val searchState by databaseOpenerViewModel.searchFoodState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    // ✅ Start DB copy
    LaunchedEffect(Unit) {
        databaseOpenerViewModel.copyDatabase()
    }

    // ✅ Trigger data load when copy is done
    LaunchedEffect(copyDatabaseState) {
        if (!copyDatabaseState.isLoading && copyDatabaseState.error.isEmpty()) {
            databaseOpenerViewModel.getAllDataFromDatabase()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // ✅ Use theme background
    ) {

        // 🔍 Search bar with themed colors
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                databaseOpenerViewModel.searchFood(it)
            },
            label = { Text("Search Food...", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true

        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                copyDatabaseState.isLoading || allFoodState.isLoading || searchState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary // ✅ Themed loader
                    )
                }

                copyDatabaseState.error.isNotEmpty() -> {
                    Text(
                        text = copyDatabaseState.error,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                allFoodState.error.isNotEmpty() -> {
                    Text(
                        text = allFoodState.error,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // ✅ Show search results if user typed something
                searchQuery.isNotBlank() -> {
                    if (searchState.data.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(searchState.data) { food -> FoodCard(food) }
                        }
                    } else {
                        Text(
                            text = "No matching food found",
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                // ✅ Otherwise show all food
                allFoodState.data.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(allFoodState.data) { food -> FoodCard(food) }
                    }
                }

                else -> {
                    Text(
                        text = "No Food Data Found",
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
