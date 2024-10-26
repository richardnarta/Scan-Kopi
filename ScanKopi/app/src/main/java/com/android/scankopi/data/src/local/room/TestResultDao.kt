package com.android.scankopi.data.src.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.helper.Constant.TABLE_NAME

@Dao
interface TestResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestResult(test: TestResultEntity)

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAllTestResult(): List<TestResultEntity>

    @Query("SELECT * FROM $TABLE_NAME ORDER BY time DESC LIMIT 3")
    suspend fun getLatestTestResult(): List<TestResultEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE id=:id")
    suspend fun getTestResultById(id: Int): TestResultEntity

    @Query("DELETE FROM $TABLE_NAME WHERE id=:id")
    suspend fun deleteTestResult(id: Int)
}