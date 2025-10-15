package com.scrymz.bitebuddy.di.modules

import com.scrymz.bitebuddy.domain.repository.WaterIntakeRepository
import com.scrymz.bitebuddy.domain.usecases.DeleteWaterIntakeUseCase
import com.scrymz.bitebuddy.domain.usecases.DeleteWaterIntakesByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllWaterIntakesDescendingUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAverageWaterByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.GetTotalWaterByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetTotalWaterByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.GetTotalWaterByYearUseCase
import com.scrymz.bitebuddy.domain.usecases.GetWaterIntakeByIdUseCase
import com.scrymz.bitebuddy.domain.usecases.GetWaterIntakesByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetWaterIntakesByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.GetWaterIntakesByYearUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertWaterIntakeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterIntakeUseCaseModule {

    @Provides
    @Singleton
    fun provideUpsertWaterIntakeUseCase(repository: WaterIntakeRepository): UpsertWaterIntakeUseCase {
        return UpsertWaterIntakeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteWaterIntakeUseCase(repository: WaterIntakeRepository): DeleteWaterIntakeUseCase {
        return DeleteWaterIntakeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllWaterIntakesDescendingUseCase(repository: WaterIntakeRepository): GetAllWaterIntakesDescendingUseCase {
        return GetAllWaterIntakesDescendingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWaterIntakesByDateUseCase(repository: WaterIntakeRepository): GetWaterIntakesByDateUseCase {
        return GetWaterIntakesByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWaterIntakesByMonthUseCase(repository: WaterIntakeRepository): GetWaterIntakesByMonthUseCase {
        return GetWaterIntakesByMonthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWaterIntakeByIdUseCase(repository: WaterIntakeRepository): GetWaterIntakeByIdUseCase {
        return GetWaterIntakeByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWaterIntakesByYearUseCase(repository: WaterIntakeRepository): GetWaterIntakesByYearUseCase {
        return GetWaterIntakesByYearUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalWaterByDateUseCase(repository: WaterIntakeRepository): GetTotalWaterByDateUseCase {
        return GetTotalWaterByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalWaterByMonthUseCase(repository: WaterIntakeRepository): GetTotalWaterByMonthUseCase {
        return GetTotalWaterByMonthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalWaterByYearUseCase(repository: WaterIntakeRepository): GetTotalWaterByYearUseCase {
        return GetTotalWaterByYearUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAverageWaterByMonthUseCase(repository: WaterIntakeRepository): GetAverageWaterByMonthUseCase {
        return GetAverageWaterByMonthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteWaterIntakesByDateUseCase(repository: WaterIntakeRepository): DeleteWaterIntakesByDateUseCase {
        return DeleteWaterIntakesByDateUseCase(repository)
    }
}