package com.scrymz.bitebuddy.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.PlaylistAddCheck
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var selectedindex by rememberSaveable { mutableStateOf(0) }
    val bottonNavList = listOf<BottomNaviagtionItem>(
        BottomNaviagtionItem(
            title = "Status",
            icon = Icons.Outlined.GraphicEq
        ),
        BottomNaviagtionItem(
            title = "Food",
            icon = Icons.Outlined.List
        ),
        BottomNaviagtionItem(
            title = "Micro",
            icon = Icons.Outlined.LibraryMusic
        ),
        BottomNaviagtionItem(
            title = "Periods",
            icon = Icons.Outlined.PlaylistAddCheck
        ),
        BottomNaviagtionItem(
            title = "Settings",
            icon = Icons.Outlined.Settings
        )


    )

    Scaffold(
        bottomBar = {
            NavigationBar() {
                bottonNavList.forEachIndexed{index,item->
                    NavigationBarItem(
                        selected = index == selectedindex,
                        onClick = {
                            selectedindex = index
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.title,
                                tint = if(index==selectedindex) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary)
                        },
                        label = {
                            Text(text = item.title, color = if(index==selectedindex) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.secondary)
                        }

                    )

                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)){
            ContentScreen(navController =navController, index =  selectedindex)
        }

    }

}

data class BottomNaviagtionItem(
    val title: String,
    val icon: ImageVector
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(navController: NavController,index: Int) {

    when(index){
        0->{
            StatusScreen(navController = navController)

        }
        1->{
            ListOfAllFood(navController = navController)

        }
        2->{
            MicroDetailsScreen(navController = navController)


        }
        3->{
            MenstrualScreen()


        }
        4->{
            SettingsScreen(navController = navController)

        }

    }

}
