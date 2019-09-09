package com.rodolfogusson.testepag.infrastructure.service

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Simplifies writing callbacks in Retrofit network calls,
 * using this function and passing lambda expressions
 * as arguments.
 */
fun <T> callback(
    failure: (Throwable?) -> Unit = { error -> throw error ?: Throwable()},
    response: (Response<T>?) -> Unit
): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>?, r: Response<T>?) = response(r)
        override fun onFailure(call: Call<T>?, t: Throwable?) = failure(t)
    }
}