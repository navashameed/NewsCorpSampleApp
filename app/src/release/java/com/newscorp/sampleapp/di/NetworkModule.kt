package com.newscorp.sampleapp.di

import android.content.Context
import com.newscorp.sampleapp.network.MockNetworkHubImpl
import com.newscorp.sampleapp.network.NetworkHub
import com.newscorp.sampleapp.network.NetworkHubImpl
import com.newscorp.sampleapp.network.retrofit.NewsApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideNetworkHub(
        newsApiInterface: NewsApiInterface,
        @ApplicationContext appContext: Context
    ): NetworkHub {
        return NetworkHubImpl(newsApiInterface)
    }
}