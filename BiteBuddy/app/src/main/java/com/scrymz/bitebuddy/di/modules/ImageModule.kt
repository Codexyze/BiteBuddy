package com.scrymz.bitebuddy.di.modules

import android.content.Context
import com.scrymz.bitebuddy.data.repoImpl.ImageRepoImpl
import com.scrymz.bitebuddy.domain.repository.ImageRepository
import com.scrymz.bitebuddy.domain.usecases.GetAllImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ImageModule {
    @Singleton
    @Provides
    fun provideImageRepo(@ApplicationContext context: Context): ImageRepository{
        return ImageRepoImpl(context = context)
    }

    @Provides
    fun provideGetAllImageUseCase(imageRepository: ImageRepository): GetAllImageUseCase{
        return GetAllImageUseCase(imageRepository =imageRepository )
    }
}