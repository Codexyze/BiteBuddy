package com.scrymz.bitebuddy.core

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.scrymz.bitebuddy.Constants.Constants
import com.scrymz.bitebuddy.data.local.Food
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class DataBaseOpenHelper @Inject constructor(private val context: Context) {
    suspend fun copyDatabase(): Flow<ResultState<String>> = flow{
        emit(ResultState.loading)
        val database = Constants.DATABASENAME
        val dbPath = context.getDatabasePath(database)
        if(dbPath.exists()){
            emit(ResultState.Sucess("Sucessfull"))

        }else{
            try {
                dbPath?.parentFile?.mkdirs()
                val allDatabaseData = context.assets.open(database).readBytes()
                dbPath.writeBytes(allDatabaseData)
                Log.d("DB", "Database copied successfully!")
                emit(ResultState.Sucess("Sucessfull"))

            }catch (e: Exception){
                Log.e("DB", "Failed to copy database ${e.message}")
                emit(ResultState.Error("Failed message : ${e.message}"))

            }

        }

    }
    suspend fun getAllDataFromDatabase(): Flow<ResultState<List<Food>>> =flow{
        val listOfFood = mutableListOf<Food>()
        val database = Constants.DATABASENAME
        val dbPath = context.getDatabasePath(database).absolutePath
        try {
            emit(ResultState.loading)
            val sqlDatabase = SQLiteDatabase.openDatabase(dbPath,null, SQLiteDatabase.OPEN_READONLY)
            val cursor = sqlDatabase.rawQuery("SELECT * FROM food_table",null)
            cursor.use {cusrorElement->
                while (cusrorElement.moveToNext()){
                    val id = cursor.getColumnIndex("id")
                    val type = cursor.getColumnIndex("type")
                    val foodname = cursor.getColumnIndex("foodname")
                    val pergram = cursor.getColumnIndex("pergram")
                    val calories = cursor.getColumnIndex("calories")
                    val protein = cursor.getColumnIndex("protein")
                    val calcium = cursor.getColumnIndex("calcium")
                    val iron = cursor.getColumnIndex("iron")
                    val magnesium = cursor.getColumnIndex("magnesium")
                    val vitA = cursor.getColumnIndex("vit_a")
                    val vitB12 = cursor.getColumnIndex("vit_b12")
                    val vitC = cursor.getColumnIndex("vit_c")
                    val vitD = cursor.getColumnIndex("vit_d")
                    val safeInPregnancy = cursor.getColumnIndex("safe_in_pregnancy")
                    val menstrualSafe = cursor.getColumnIndex("menstrual_safe")
                    val femaleImportant = cursor.getColumnIndex("femaleImportant")
                    val maleImportant = cursor.getColumnIndex("maleImportant")
                    val food = Food(
                        id = cursor.getInt(id),
                        type = cursor.getString(type),
                        foodname = cursor.getString(foodname),
                        pergram = cursor.getDouble(pergram),
                        calories = cursor.getDouble(calories),
                        protein = cursor.getDouble(protein),
                        calcium = cursor.getDouble(calcium),
                        iron = cursor.getDouble(iron),
                        magnesium = cursor.getDouble(magnesium),
                        vitA = cursor.getDouble(vitA),
                        vitB12 = cursor.getDouble(vitB12),
                        vitC = cursor.getDouble(vitC),
                        vitD = cursor.getDouble(vitD),
                        safeInPregnancy = cursor.getInt(safeInPregnancy) == 1,
                        menstrualSafe = cursor.getInt(menstrualSafe) == 1,
                        femaleImportant = cursor.getInt(femaleImportant) == 1,
                        maleImportant = cursor.getInt(maleImportant) == 1
                    )
                    listOfFood.add(food)
                }
                if(!listOfFood.isNullOrEmpty()){
                    emit(ResultState.Sucess(listOfFood))
                }else{
                    emit(ResultState.Error("No Data Found"))
                }


            }

        }catch (e: kotlin.Exception){
            emit(ResultState.Error("Error : ${e.message}"))
        }



    }


}
// val id: Int = 0,                        // INTEGER PRIMARY KEY AUTOINCREMENT
//    val type: String? = null,               // TEXT (nullable)
//    val foodname: String,                   // TEXT UNIQUE (required)
//    val pergram: Double? = null,            // REAL (nullable)
//    val calories: Double? = null,           // REAL (nullable)
//    val protein: Double? = null,            // REAL (nullable)
//    val calcium: Double? = null,            // REAL (nullable)
//    val iron: Double? = null,               // REAL (nullable)
//    val magnesium: Double? = null,          // REAL (nullable)
//    val vitA: Double? = null,               // REAL (vit_a)
//    val vitB12: Double? = null,             // REAL (vit_b12)
//    val vitC: Double? = null,               // REAL (vit_c)
//    val vitD: Double? = null,               // REAL (vit_d)
//    val safeInPregnancy: Boolean = false,   // INTEGER (0/1 -> Boolean)
//    val menstrualSafe: Boolean = false,     // INTEGER (0/1 -> Boolean)
//    val femaleImportant: Boolean = false,   // INTEGER (female_important)
//    val maleImportant: Boolean = false      // INTEGER (male_important)