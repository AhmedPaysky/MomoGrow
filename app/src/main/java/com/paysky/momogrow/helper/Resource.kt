package com.paysky.momogrow.helper

import android.annotation.SuppressLint
import android.util.Log
import retrofit2.HttpException

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

//        fun <T> error(data: T?, message: String?): Resource<T> =
//            Resource(status = Status.ERROR, data = data, message = message)


        @SuppressLint("LogNotTimber")
        fun <T> error(data: T?, message: String?): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)


        @SuppressLint("LogNotTimber")
        fun <T> errorHttp(data: T?, message: String?): Resource<T> {
            //handle error response
            if (data is HttpException)
                Log.v("HttpError", data.response()?.code().toString())
            return Resource(status = Status.ERRORHttp, data = data, message = message)
        }

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}

enum class Status {
    SUCCESS,
    ERRORHttp,
    ERROR,
    LOADING
}