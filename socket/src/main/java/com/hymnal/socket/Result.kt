package com.hymnal.socket

class Result<T>(private val data: T?, private val exception: Exception? = null) {

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(data)
        }

        fun <T> failure(exception: Exception): Result<T> {
            return Result(null, exception)
        }
    }

    val isSuccess: Boolean
        get() = exception == null
    val isFailure: Boolean
        get() = exception != null


    fun getOrNull(): T? {
        return data
    }

    fun exceptionOrNull(): Exception? {
        return exception
    }

    fun getOrThrow(): T {
        if (exception != null) throw exception
        if (data == null) throw  Exception("data is null")
        return data
    }

    override fun toString(): String {
        return if (isSuccess) {
            "Success(${data})"
        } else {
            "Failure(${exception})"
        }
    }

}