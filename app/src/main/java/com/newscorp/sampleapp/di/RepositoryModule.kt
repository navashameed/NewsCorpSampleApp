package com.newscorp.sampleapp.di

import com.newscorp.sampleapp.repository.NewsCorpRepository
import com.newscorp.sampleapp.repository.NewsCorpRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindNewsCorpRepository(repository: NewsCorpRepositoryImpl): NewsCorpRepository

}