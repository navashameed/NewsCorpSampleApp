package com.newscorp.sampleapp.di

import android.content.Context
import android.content.SharedPreferences
import com.newscorp.sampleapp.network.MockNetworkHubImpl
import com.newscorp.sampleapp.network.NetworkHub
import com.newscorp.sampleapp.network.NetworkHubImpl
import com.newscorp.sampleapp.network.retrofit.NewsApiInterface
import com.newscorp.sampleapp.network.utils.NetworkConstants
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
        sharedPreferences: SharedPreferences,
        newsApiInterface: NewsApiInterface,
        @ApplicationContext appContext: Context
    ): NetworkHub {
        if (sharedPreferences.getBoolean(NetworkConstants.PREF_KEY_MOCK_MODE, false)) {
            return MockNetworkHubImpl(appContext)
        } else {
            return NetworkHubImpl(newsApiInterface)
        }
    }
}