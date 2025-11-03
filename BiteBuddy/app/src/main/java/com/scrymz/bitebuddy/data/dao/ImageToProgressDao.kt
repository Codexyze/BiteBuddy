package com.scrymz.bitebuddy.data.dao

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.scrymz.bitebuddy.data.entity.ImageToProgress
import kotlinx.coroutines.flow.Flow

@Keep
@Dao
interface ImageToProgressDao {
    @Insert
    suspend fun insertImageToProgress(imageToProgress: ImageToProgress)

    @Update
    suspend fun updateImageToProgress(imageToProgress: ImageToProgress)

    @Delete
    suspend fun deleteImageToProgress(imageToProgress: ImageToProgress)

    @Query("SELECT * FROM ImageToProgressTable ORDER BY imgDate DESC")
    fun getAllImageToProgressDescending(): Flow<List<ImageToProgress>>

    @Query("SELECT * FROM ImageToProgressTable WHERE imgDate = :date ORDER BY id DESC")
    fun getImageToProgressByDate(date: String): Flow<List<ImageToProgress>>

    @Query("SELECT * FROM ImageToProgressTable WHERE id = :id")
    suspend fun getImageToProgressById(id: Int): ImageToProgress?

    @Query("SELECT * FROM ImageToProgressTable WHERE imageAuthor = :author ORDER BY imgDate DESC")
    fun getImageToProgressByAuthor(author: String): Flow<List<ImageToProgress>>

    @Query("DELETE FROM ImageToProgressTable WHERE id = :id")
    suspend fun deleteImageToProgressById(id: Int)

    @Query("SELECT COUNT(*) FROM ImageToProgressTable")
    suspend fun getImageToProgressCount(): Int
}

