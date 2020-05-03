package com.pedroroig.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.pedroroig.cache.db.ConfigConstants.QUERY_CONFIG
import com.pedroroig.cache.model.Config
import io.reactivex.Flowable

@Dao
abstract class ConfigDao {

    @Query(QUERY_CONFIG)
    abstract fun getConfig(): Flowable<Config>

    @Insert(onConflict = REPLACE)
    abstract fun insertConfig(config: Config)


}