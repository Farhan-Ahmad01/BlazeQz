package com.example.blazeqz.presentation.result

sealed interface ResultEvent {
    data class ShowToast(val message: String): ResultEvent
}