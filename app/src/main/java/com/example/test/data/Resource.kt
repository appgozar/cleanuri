package com.example.test.data

class Resource<out T>(val status : Int, val data : T?) {


    companion object{
        const val IDLE = 0
        const val LOADING = 1
        const val ERROR = 2

        @JvmStatic
        fun <T> getLoading() = Resource<T>(LOADING, null)
        @JvmStatic
        fun <T> getError() = Resource<T>(ERROR, null)
        @JvmStatic
        fun <T> getIdle() = Resource<T>(IDLE, null)
    }
}