package com.scrymz.bitebuddy.di.modules

import com.scrymz.bitebuddy.data.dao.ImageToProgressDao
import com.scrymz.bitebuddy.data.repoImpl.ImageToProgressRepositoryImpl
import com.scrymz.bitebuddy.domain.repository.ImageToProgressRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageToProgressModule {


    @Provides
    @Singleton
    fun provideImageToProgressRepository(dao: ImageToProgressDao): ImageToProgressRepository {
        return ImageToProgressRepositoryImpl(dao)
    }
}

