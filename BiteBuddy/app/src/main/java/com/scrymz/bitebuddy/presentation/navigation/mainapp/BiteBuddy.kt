package com.scrymz.bitebuddy.presentation.navigation.mainapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.scrymz.bitebuddy.presentation.navigation.routes.FOODINFOSCREEN
import com.scrymz.bitebuddy.presentation.navigation.routes.HOMESCREEN
import com.scrymz.bitebuddy.presentation.screens.FoodInfoScreen
import com.scrymz.bitebuddy.presentation.screens.HomeScreen

@Composable
fun BiteBuddy(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = HOMESCREEN ){
        composable<HOMESCREEN>{
            HomeScreen(navController = navController)
        }
        composable<FOODINFOSCREEN> {backstackEntry->
            val data: FOODINFOSCREEN = backstackEntry.toRoute()
            FoodInfoScreen(navController = navController,
                id =data.id,
                type =data.type,
                foodname =data.foodname,
                pergram =data.pergram,
                calories =data.calories,
                protein =data.protein,
                calcium =data.calcium,
                iron =data.iron,
                magnesium =data.magnesium,
                vitA =data.vitA,
                vitB12 =data.vitB12,
                vitC =data.vitC,
                vitD =data.vitD,
                safeInPregnancy =data.safeInPregnancy,
                menstrualSafe =data.menstrualSafe,
                femaleImportant =data.femaleImportant,
                maleImportant =data.maleImportant
            )

        }
    }
    
}