package com.android.scankopi.helper

sealed class Result <out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val error: String): Result<Nothing>()
    data object Loading: Result<Nothing>()
}