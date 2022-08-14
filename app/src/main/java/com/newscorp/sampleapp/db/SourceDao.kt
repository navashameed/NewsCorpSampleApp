package com.newscorp.sampleapp.db

import androidx.room.*
import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.repository.model.Source

@Dao
interface SourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(entity: Source): Long

    @Delete
    suspend fun deleteSource(entity: Source): Int?

    @Query("SELECT * FROM source")
    suspend fun getSources(): List<Source>?

}
