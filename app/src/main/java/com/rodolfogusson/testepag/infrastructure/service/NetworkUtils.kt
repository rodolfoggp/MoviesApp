package com.rodolfogusson.testepag.infrastructure.service

import com.rodolfogusson.testepag.infrastructure.data.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Simplifies writing callbacks in Retrofit network calls.
 */
fun <T> then(
    function: (resource: Resource<T>) -> Unit
): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            function(Resource.success(response?.body()))
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            function(Resource.error(t))
        }
    }
}

