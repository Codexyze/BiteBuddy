package com.scrymz.bitebuddy.data.repoImpl

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.scrymz.bitebuddy.data.local.model.model.Images
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageRepoImpl(
    private val context: Context
): ImageRepository {
    override suspend fun getAllImage(): Flow<ResultState<List<Images>>> = flow{
        val imageList = mutableListOf<Images>()
        val contentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        try {
            emit(ResultState.loading)

            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // ✅ correct URI
                projection,
                null,
                null,
                null
            )

            cursor?.use { cursorElement ->
                val idColumn = cursorElement.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursorElement.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                while (cursorElement.moveToNext()) {
                    val id = cursorElement.getLong(idColumn) // ✅ get as Long
                    val name = cursorElement.getString(nameColumn)

                    // ✅ Build content:// Uri for the image
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    val image = Images(
                        path = contentUri.toString(), // ✅ save as string
                        name = name
                    )

                    imageList.add(image)
                }
                //emit(ResultState.Sucess(imageList))
            }

            emit(ResultState.Sucess(imageList))
        } catch (e: Exception) {
            emit(ResultState.Error(e.toString()))
        }
    }
}