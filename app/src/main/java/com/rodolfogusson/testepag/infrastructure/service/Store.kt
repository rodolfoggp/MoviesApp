package com.rodolfogusson.testepag.infrastructure.service

/**
 * Class used to retrieve the api key from the "keys.c" file.
 */
class Store {

    companion object {
        init {
            System.loadLibrary("keys")
        }
    }

    external fun getApiKey(): String
}