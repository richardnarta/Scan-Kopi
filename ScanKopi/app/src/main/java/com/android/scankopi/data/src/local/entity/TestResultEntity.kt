package com.android.scankopi.data.src.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.scankopi.helper.Constant.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TestResultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var testId: Int,

    @ColumnInfo(name = "time")
    var testTimeStamp: String,

    @ColumnInfo(name = "image")
    var testImage: String,

    @ColumnInfo(name = "description")
    var testDescription: String,

    @ColumnInfo(name = "sni")
    var testSniScore: Int,

    @ColumnInfo(name = "scaa")
    var testScaaScore: Int,

    @ColumnInfo(name = "detail")
    var testDetail: String,
)
