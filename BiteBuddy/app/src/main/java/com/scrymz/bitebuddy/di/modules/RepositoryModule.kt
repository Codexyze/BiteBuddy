package com.scrymz.bitebuddy.di.modules

import com.scrymz.bitebuddy.data.dao.FoodTableDao
import com.scrymz.bitebuddy.data.dao.MenstrualDao
import com.scrymz.bitebuddy.data.repoImpl.FoodDatabaseRepositoryImpl
import com.scrymz.bitebuddy.data.repoImpl.MenstrualRepositoryImpl
import com.scrymz.bitebuddy.domain.repository.FoodDatabaseRepository
import com.scrymz.bitebuddy.domain.repository.MenstrualRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFoodRepository(dao: FoodTableDao): FoodDatabaseRepository{
        return FoodDatabaseRepositoryImpl(dao = dao)
    }
    @Provides
    @Singleton
    fun provideMenstrualRepository(dao: MenstrualDao): MenstrualRepository {
        return MenstrualRepositoryImpl(dao =dao )

    }

}