package com.scrymz.bitebuddy.data.repoImpl

import com.scrymz.bitebuddy.data.dao.ImageToProgressDao
import com.scrymz.bitebuddy.data.entity.ImageToProgress
import com.scrymz.bitebuddy.domain.StateHandeling.ResultState
import com.scrymz.bitebuddy.domain.repository.ImageToProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageToProgressRepositoryImpl @Inject constructor(
    private val dao: ImageToProgressDao
) : ImageToProgressRepository {

    override fun upsertImageToProgress(imageToProgress: ImageToProgress): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            if (imageToProgress.id == 0) {
                dao.insertImageToProgress(imageToProgress)
                emit(ResultState.Sucess("Progress image added successfully"))
            } else {
                dao.updateImageToProgress(imageToProgress)
                emit(ResultState.Sucess("Progress image updated successfully"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun deleteImageToProgress(imageToProgress: ImageToProgress): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.deleteImageToProgress(imageToProgress)
            emit(ResultState.Sucess("Progress image deleted successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getAllImageToProgressDescending(): Flow<ResultState<List<ImageToProgress>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getAllImageToProgressDescending().collect { images ->
                emit(ResultState.Sucess(images))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getImageToProgressByDate(date: String): Flow<ResultState<List<ImageToProgress>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getImageToProgressByDate(date).collect { images ->
                emit(ResultState.Sucess(images))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getImageToProgressById(id: Int): Flow<ResultState<ImageToProgress?>> = flow {
        emit(ResultState.loading)
        try {
            val image = dao.getImageToProgressById(id)
            emit(ResultState.Sucess(image))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getImageToProgressByAuthor(author: String): Flow<ResultState<List<ImageToProgress>>> = flow {
        emit(ResultState.loading)
        try {
            dao.getImageToProgressByAuthor(author).collect { images ->
                emit(ResultState.Sucess(images))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun deleteImageToProgressById(id: Int): Flow<ResultState<String>> = flow {
        emit(ResultState.loading)
        try {
            dao.deleteImageToProgressById(id)
            emit(ResultState.Sucess("Progress image deleted successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getImageToProgressCount(): Flow<ResultState<Int>> = flow {
        emit(ResultState.loading)
        try {
            val count = dao.getImageToProgressCount()
            emit(ResultState.Sucess(count))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }
}

