package com.newscorp.sampleapp.network

import android.content.Context
import androidx.annotation.RawRes
import com.newscorp.sampleapp.R
import com.newscorp.sampleapp.di.DateAdapter
import com.newscorp.sampleapp.network.model.ArticleResponse
import com.newscorp.sampleapp.network.model.SourceResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.delay
import okio.buffer
import okio.source
import javax.inject.Inject

class MockNetworkHubImpl @Inject constructor(private val context: Context) : NetworkHub {
    override suspend fun getHeadlines(sources: String): ArticleResponse? {
        delay(DELAY_IN_MILLISECONDS)
        return readJsonFromResource(R.raw.headlines) as? ArticleResponse
    }

    override suspend fun getSources(): SourceResponse? {
        delay(DELAY_IN_MILLISECONDS)
        return readJsonFromResource(R.raw.headlines) as? SourceResponse
    }

    private inline fun <reified T> readJsonFromResource(@RawRes rawResId: Int): T? {
        context.resources.openRawResource(rawResId).use { stream ->
            val moshi: Moshi = Moshi.Builder().add(DateAdapter).build()
            val adapter: JsonAdapter<T> =
                moshi.adapter(T::class.java)
            return adapter.fromJson(stream.source().buffer())
        }
    }

    companion object {
        const val DELAY_IN_MILLISECONDS = 1000L
    }
}