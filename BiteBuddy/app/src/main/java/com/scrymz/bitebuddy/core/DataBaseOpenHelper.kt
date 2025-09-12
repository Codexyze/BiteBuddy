package com.scrymz.bitebuddy.core

import android.annotation.SuppressLint
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


class DataBaseOpenHelper @Inject constructor(private val context: Context) {

    suspend fun copyDatabase(): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        val database = Constants.DATABASENAME
        val dbPath = context.getDatabasePath(database)
        Log.d("DB", "Checking if database exists at: $dbPath")
        if (dbPath.exists()) {
            Log.d("DB", "Database already exists, no need to copy")
            emit(ResultState.Sucess("Already Exists"))
        } else {
            try {
                Log.d("DB", "Database does NOT exist. Copying from assets...")
                dbPath.parentFile?.mkdirs()
                val allDatabaseData = context.assets.open(database).readBytes()
                dbPath.writeBytes(allDatabaseData)
                Log.d("DB", "Database copied successfully!")
                emit(ResultState.Sucess("Copied Successfully"))
            } catch (e: Exception) {
                Log.e("DB", "Failed to copy database: ${e.message}", e)
                emit(ResultState.Error("Failed message : ${e.message}"))
            }
        }
    }

    @SuppressLint("Range")
    suspend fun getAllDataFromDatabase(): Flow<ResultState<List<Food>>> = flow {
        Log.d("DB", "getAllDataFromDatabase() called")
        val listOfFood = mutableListOf<Food>()
        val database = Constants.DATABASENAME
        val dbPath = context.getDatabasePath(database).absolutePath
        Log.d("DB", "Opening DB at: $dbPath")

        try {
            emit(ResultState.loading)
            val sqlDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
            Log.d("DB", "DB opened successfully")
            val cursor = sqlDatabase.rawQuery("SELECT * FROM food_table", null)
            Log.d("DB", "Query executed, row count: ${cursor.count}")

            cursor.use { c ->
                while (c.moveToNext()) {
                    val foodName = c.getString(c.getColumnIndex("foodname"))
                    Log.d("DB", "Reading row: $foodName")
                    // Construct object (unchanged)
                    val food = Food(
                        id = c.getInt(c.getColumnIndex("id")),
                        type = c.getString(c.getColumnIndex("type")),
                        foodname = foodName,
                        pergram = c.getDouble(c.getColumnIndex("pergram")),
                        calories = c.getDouble(c.getColumnIndex("calories")),
                        protein = c.getDouble(c.getColumnIndex("protein")),
                        calcium = c.getDouble(c.getColumnIndex("calcium")),
                        iron = c.getDouble(c.getColumnIndex("iron")),
                        magnesium = c.getDouble(c.getColumnIndex("magnesium")),
                        vitA = c.getDouble(c.getColumnIndex("vit_a")),
                        vitB12 = c.getDouble(c.getColumnIndex("vit_b12")),
                        vitC = c.getDouble(c.getColumnIndex("vit_c")),
                        vitD = c.getDouble(c.getColumnIndex("vit_d")),
                        safeInPregnancy = c.getInt(c.getColumnIndex("safeInPregnancy")) == 1,
                        menstrualSafe = c.getInt(c.getColumnIndex("menstrualSafe")) == 1,
                        femaleImportant = c.getInt(c.getColumnIndex("female_important")) == 1,
                        maleImportant = c.getInt(c.getColumnIndex("male_important")) == 1
                    )
                    listOfFood.add(food)
                }
            }

            sqlDatabase.close()
            if (listOfFood.isNotEmpty()) {
                Log.d("DB", "Total food items fetched: ${listOfFood.size}")
                emit(ResultState.Sucess(listOfFood))
            } else {
                Log.e("DB", "No data found in food_table")
                emit(ResultState.Error("No Data Found"))
            }

        } catch (e: Exception) {
            Log.e("DB", "Error reading from DB: ${e.message}")
            emit(ResultState.Error("Error: ${e.message}"))
        }
    }

    @SuppressLint("Range")
    suspend fun searchFoodByName(query: String): Flow<ResultState<List<Food>>> = flow {
        Log.d("DB", "searchFoodByName() called with query: $query")
        val resultList = mutableListOf<Food>()
        val database = Constants.DATABASENAME
        val dbPath = context.getDatabasePath(database).absolutePath

        try {
            emit(ResultState.loading)
            val sqlDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)

            // ✅ Use LOWER() to make search case-insensitive
            val sqlQuery = "SELECT * FROM food_table WHERE LOWER(foodname) LIKE ?"
            val selectionArgs = arrayOf("%${query.lowercase()}%")

            val cursor = sqlDatabase.rawQuery(sqlQuery, selectionArgs)
            Log.d("DB", "Search executed, found ${cursor.count} rows for query: $query")

            cursor.use { c ->
                while (c.moveToNext()) {
                    val foodName = c.getString(c.getColumnIndex("foodname"))
                    Log.d("DB", "Matched row: $foodName")
                    val food = Food(
                        id = c.getInt(c.getColumnIndex("id")),
                        type = c.getString(c.getColumnIndex("type")),
                        foodname = foodName,
                        pergram = c.getDouble(c.getColumnIndex("pergram")),
                        calories = c.getDouble(c.getColumnIndex("calories")),
                        protein = c.getDouble(c.getColumnIndex("protein")),
                        calcium = c.getDouble(c.getColumnIndex("calcium")),
                        iron = c.getDouble(c.getColumnIndex("iron")),
                        magnesium = c.getDouble(c.getColumnIndex("magnesium")),
                        vitA = c.getDouble(c.getColumnIndex("vit_a")),
                        vitB12 = c.getDouble(c.getColumnIndex("vit_b12")),
                        vitC = c.getDouble(c.getColumnIndex("vit_c")),
                        vitD = c.getDouble(c.getColumnIndex("vit_d")),
                        safeInPregnancy = c.getInt(c.getColumnIndex("safeInPregnancy")) == 1,
                        menstrualSafe = c.getInt(c.getColumnIndex("menstrualSafe")) == 1,
                        femaleImportant = c.getInt(c.getColumnIndex("female_important")) == 1,
                        maleImportant = c.getInt(c.getColumnIndex("male_important")) == 1
                    )
                    resultList.add(food)
                }
            }

            sqlDatabase.close()
            if (resultList.isNotEmpty()) {
                emit(ResultState.Sucess(resultList))
            } else {
                emit(ResultState.Error("No matches found for \"$query\""))
            }
        } catch (e: Exception) {
            Log.e("DB", "Error while searching: ${e.message}")
            emit(ResultState.Error("Error searching: ${e.message}"))
        }
    }


}
