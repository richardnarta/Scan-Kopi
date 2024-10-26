package com.android.scankopi.data.src.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.scankopi.data.src.local.entity.TestResultEntity

@Database(
    entities = [TestResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract fun testResultDao(): TestResultDao
}