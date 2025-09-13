package com.scrymz.bitebuddy.di.modules

import com.scrymz.bitebuddy.domain.repository.FoodDatabaseRepository
import com.scrymz.bitebuddy.domain.usecases.DeleteFoodUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllFoodUseCase
import com.scrymz.bitebuddy.domain.usecases.GetByConsumedTimeUseCase
import com.scrymz.bitebuddy.domain.usecases.GetByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetCaloriesUseCase
import com.scrymz.bitebuddy.domain.usecases.GetProteinUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertFoodUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object FoodUseCaseModule {

    @Provides
    fun provideUpsertFoodUseCase(repo: FoodDatabaseRepository): UpsertFoodUseCase =
        UpsertFoodUseCase(repo)

    @Provides
    fun provideDeleteFoodUseCase(repo: FoodDatabaseRepository): DeleteFoodUseCase =
        DeleteFoodUseCase(repo)

    @Provides
    fun provideGetAllFoodUseCase(repo: FoodDatabaseRepository): GetAllFoodUseCase =
        GetAllFoodUseCase(repo)

    @Provides
    fun provideGetByDateUseCase(repo: FoodDatabaseRepository): GetByDateUseCase =
        GetByDateUseCase(repo)

    @Provides
    fun provideGetByConsumedTimeUseCase(repo: FoodDatabaseRepository): GetByConsumedTimeUseCase =
        GetByConsumedTimeUseCase(repo)

    @Provides
    fun provideGetProteinUseCase(repo: FoodDatabaseRepository): GetProteinUseCase =
        GetProteinUseCase(repo)

    @Provides
    fun provideGetCaloriesUseCase(repo: FoodDatabaseRepository): GetCaloriesUseCase =
        GetCaloriesUseCase(repo)
}
