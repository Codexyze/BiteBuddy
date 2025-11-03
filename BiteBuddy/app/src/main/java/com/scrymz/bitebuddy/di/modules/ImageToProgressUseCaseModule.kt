package com.scrymz.bitebuddy.di.modules

import com.scrymz.bitebuddy.domain.repository.ImageToProgressRepository
import com.scrymz.bitebuddy.domain.usecases.DeleteImageToProgressUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllImageToProgressDescendingUseCase
import com.scrymz.bitebuddy.domain.usecases.GetImageToProgressByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetImageToProgressByIdUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertImageToProgressUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageToProgressUseCaseModule {

    @Provides
    @Singleton
    fun provideUpsertImageToProgressUseCase(repository: ImageToProgressRepository): UpsertImageToProgressUseCase {
        return UpsertImageToProgressUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteImageToProgressUseCase(repository: ImageToProgressRepository): DeleteImageToProgressUseCase {
        return DeleteImageToProgressUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllImageToProgressDescendingUseCase(repository: ImageToProgressRepository): GetAllImageToProgressDescendingUseCase {
        return GetAllImageToProgressDescendingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetImageToProgressByDateUseCase(repository: ImageToProgressRepository): GetImageToProgressByDateUseCase {
        return GetImageToProgressByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetImageToProgressByIdUseCase(repository: ImageToProgressRepository): GetImageToProgressByIdUseCase {
        return GetImageToProgressByIdUseCase(repository)
    }
}

