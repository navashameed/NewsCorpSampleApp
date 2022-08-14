package com.newscorp.sampleapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.repository.model.Source

@Database(entities = [Article::class, Source::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NewsCorpDb : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    abstract fun sourcesDao(): SourceDao

    companion object {
        @Volatile
        private var INSTANCE: NewsCorpDb? = null

        fun getDatabase(context: Context): NewsCorpDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NewsCorpDb::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}