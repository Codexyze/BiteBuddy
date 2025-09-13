package com.scrymz.bitebuddy.di.modules

import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import com.scrymz.bitebuddy.domain.usecases.DeletePeriodUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllPeriodsDescendingUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodByIdUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByPainLevelUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByTimeOfDayUseCase
import com.scrymz.bitebuddy.domain.usecases.GetPeriodsByYearUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertPeriodUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MenstrualUseCaseModule {

    @Provides
    @Singleton
    fun provideUpsertPeriodUseCase(repository: MenstrualRepository): UpsertPeriodUseCase {
        return UpsertPeriodUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeletePeriodUseCase(repository: MenstrualRepository): DeletePeriodUseCase {
        return DeletePeriodUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPeriodsDescendingUseCase(repository: MenstrualRepository): GetAllPeriodsDescendingUseCase {
        return GetAllPeriodsDescendingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPeriodsByMonthUseCase(repository: MenstrualRepository): GetPeriodsByMonthUseCase {
        return GetPeriodsByMonthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPeriodsByPainLevelUseCase(repository: MenstrualRepository): GetPeriodsByPainLevelUseCase {
        return GetPeriodsByPainLevelUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPeriodsByTimeOfDayUseCase(repository: MenstrualRepository): GetPeriodsByTimeOfDayUseCase {
        return GetPeriodsByTimeOfDayUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPeriodByIdUseCase(repository: MenstrualRepository): GetPeriodByIdUseCase {
        return GetPeriodByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPeriodsByYearUseCase(repository: MenstrualRepository): GetPeriodsByYearUseCase {
        return GetPeriodsByYearUseCase(repository)
    }
}
