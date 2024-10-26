package com.android.scankopi.di

import android.content.Context
import androidx.room.Room
import com.android.scankopi.data.src.local.room.Database
import com.android.scankopi.data.src.local.room.TestResultDao
import com.android.scankopi.helper.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTestResultDatabase(
        @ApplicationContext context: Context
    ): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideTestResultDao(database: Database): TestResultDao {
        return database.testResultDao()
    }
}