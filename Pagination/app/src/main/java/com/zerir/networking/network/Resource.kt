package com.zerir.networking.network

sealed class Resource<out T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Success<out T>(data: T) : Resource<T>(data = data)
    class Failure<T>(
        data: T? = null,
        throwable: Throwable? = null,
    ) : Resource<T>(data = data, throwable = throwable)
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
}
