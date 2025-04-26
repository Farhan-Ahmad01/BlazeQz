package com.example.blazeqz.presentation.util

import com.example.blazeqz.domain.util.DataError

fun DataError.getErrorMessage(): String {
    return when (this) {
        DataError.NoInternet -> "No internet connection. Check your network"
        DataError.RequestTimeout -> "Request timed out. Try again"
        DataError.Serialization -> "Failed to process data. Try again"
        DataError.Server -> "Server error occurred. Try again"
        DataError.TooManyRequests -> "Too many requests. Please slow down"
        is DataError.Unknown -> "An unknown error occurred. ${this.errorMessage}"
    }
}