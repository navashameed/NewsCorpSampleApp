package com.newscorp.sampleapp.network

import java.io.IOException

class NetworkHttpException private constructor(val statusCode: Int, cause: Throwable?) :
    IOException(cause) {

    companion object {
        fun create(statusCode: Int): NetworkHttpException {
            return NetworkHttpException(statusCode, null)
        }

        fun create(statusCode: Int, cause: Throwable?): NetworkHttpException {
            return NetworkHttpException(statusCode, cause)
        }
    }
}