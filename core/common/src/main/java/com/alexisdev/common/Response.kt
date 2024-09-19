package com.alexisdev.common


//sealed class Response<T> {
//    data object Loading: Response<Nothing>()
//    data class Success<T>(val data: T? = null): Response<T>()
//    data class Error<T>(val msg: String? = null): Response<T>()
//}


sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
    data object Loading: Response<Nothing>()
}