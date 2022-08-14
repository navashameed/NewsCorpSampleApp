package com.newscorp.sampleapp.network

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@Throws(NetworkHttpException::class)
fun <T : Any?> Response<T>.successBodyOrException(): T? {
    try {
        return body()
    } catch (e: IOException) {
        throw NetworkHttpException.create(this.code(), e)
    } catch (e: HttpException) {
        throw NetworkHttpException.create(e.code(), e)
    }
}
