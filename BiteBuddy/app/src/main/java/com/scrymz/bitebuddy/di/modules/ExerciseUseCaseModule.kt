package com.scrymz.bitebuddy.di.modules


import com.scrymz.bitebuddy.domain.repository.ExerciseRepository
import com.scrymz.bitebuddy.domain.usecases.DeleteExerciseUseCase
import com.scrymz.bitebuddy.domain.usecases.GetAllExercisesDescendingUseCase
import com.scrymz.bitebuddy.domain.usecases.GetExerciseByIdUseCase
import com.scrymz.bitebuddy.domain.usecases.GetExercisesByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetExercisesByIntensityUseCase
import com.scrymz.bitebuddy.domain.usecases.GetExercisesByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.GetExercisesByTypeUseCase
import com.scrymz.bitebuddy.domain.usecases.GetExercisesByYearUseCase
import com.scrymz.bitebuddy.domain.usecases.GetTotalCaloriesBurnedByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetTotalCaloriesBurnedByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.GetTotalDurationByDateUseCase
import com.scrymz.bitebuddy.domain.usecases.GetTotalDurationByMonthUseCase
import com.scrymz.bitebuddy.domain.usecases.UpsertExerciseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExerciseUseCaseModule {

    @Provides
    @Singleton
    fun provideUpsertExerciseUseCase(repository: ExerciseRepository): UpsertExerciseUseCase {
        return UpsertExerciseUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteExerciseUseCase(repository: ExerciseRepository): DeleteExerciseUseCase {
        return DeleteExerciseUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllExercisesDescendingUseCase(repository: ExerciseRepository): GetAllExercisesDescendingUseCase {
        return GetAllExercisesDescendingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExercisesByDateUseCase(repository: ExerciseRepository): GetExercisesByDateUseCase {
        return GetExercisesByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExercisesByMonthUseCase(repository: ExerciseRepository): GetExercisesByMonthUseCase {
        return GetExercisesByMonthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExercisesByTypeUseCase(repository: ExerciseRepository): GetExercisesByTypeUseCase {
        return GetExercisesByTypeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExercisesByIntensityUseCase(repository: ExerciseRepository): GetExercisesByIntensityUseCase {
        return GetExercisesByIntensityUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExerciseByIdUseCase(repository: ExerciseRepository): GetExerciseByIdUseCase {
        return GetExerciseByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetExercisesByYearUseCase(repository: ExerciseRepository): GetExercisesByYearUseCase {
        return GetExercisesByYearUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalCaloriesBurnedByDateUseCase(repository: ExerciseRepository): GetTotalCaloriesBurnedByDateUseCase {
        return GetTotalCaloriesBurnedByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalDurationByDateUseCase(repository: ExerciseRepository): GetTotalDurationByDateUseCase {
        return GetTotalDurationByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalCaloriesBurnedByMonthUseCase(repository: ExerciseRepository): GetTotalCaloriesBurnedByMonthUseCase {
        return GetTotalCaloriesBurnedByMonthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalDurationByMonthUseCase(repository: ExerciseRepository): GetTotalDurationByMonthUseCase {
        return GetTotalDurationByMonthUseCase(repository)
    }
}