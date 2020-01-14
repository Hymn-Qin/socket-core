package com.hymnal.socket

object JavaAdapter {

    @JvmStatic
    fun <T> isSuccess(result: Result<T>): Boolean {
        return result.isSuccess
    }

    @JvmStatic
    fun <T> isFailure(result: Result<T>): Boolean {
        return result.isFailure
    }

    @JvmStatic
    fun <T> getData(result: Result<T>): T? {
        return result.getOrNull()
    }

    @JvmStatic
    fun <T> getException(result: Result<T>) = result.exceptionOrNull()
}