package com.example.myapplication.util

sealed class DataState<out T> {
    data class Error<out T>(val message: String?) : DataState<T>()
    class GameOver<out T> : DataState<T>()
    data class WinnerData<out T>(val playerName : String) : DataState<T>()
}