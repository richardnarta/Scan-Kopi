package com.android.scankopi.domain.model

data class TestResult(
    val description: String,
    val detail: String,
    val sni: Int,
    val scaa: Int,
    val sniDesc: String,
    val scaaDesc: String,
)