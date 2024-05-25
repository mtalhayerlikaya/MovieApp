package com.example.movieapp.common

sealed class Resource<out T> {

    class Success<out T>(val data: T?) : Resource<T>()

    class Error(val message: String, val code: String = "") : Resource<Nothing>()

    object Loading : Resource<Nothing>()

    object Empty : Resource<Nothing>()
}