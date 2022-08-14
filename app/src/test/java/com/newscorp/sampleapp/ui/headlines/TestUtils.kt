package com.newscorp.sampleapp.ui.headlines

import com.newscorp.sampleapp.di.DateAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okio.buffer
import okio.source
import java.io.IOException
import java.io.InputStream


class TestUtils {
    /* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
     * read json resource
     * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    @Throws(IOException::class)
    inline fun <reified T> readJsonResource(filename: String, cls: Class<T>): T? {
        getResourceAsStream(filename).use { stream ->
            val moshi: Moshi = Moshi.Builder().add(DateAdapter).build()
            val adapter: JsonAdapter<T> =
                moshi.adapter(T::class.java)
            return adapter.fromJson(stream.source().buffer())
        }
    }

    fun getResourceAsStream(filename: String): InputStream {
        return TestUtils::class.java.classLoader?.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("unknown resource: $filename")
    }

}


