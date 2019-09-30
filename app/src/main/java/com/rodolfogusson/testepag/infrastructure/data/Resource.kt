package com.rodolfogusson.testepag.infrastructure.data

import com.rodolfogusson.testepag.infrastructure.data.Status.*

class Resource<T>(val status: Status, val data: T?, val error: Throwable?) {

    val hasError: Boolean
        get() = status == ERROR

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(error: Throwable?, data: T? = null): Resource<T> {
            return Resource(ERROR, data, error)
        }
    }
}