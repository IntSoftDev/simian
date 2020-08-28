package com.intsoftdev.nreclient.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intsoftdev.nreclient.data.model.VersionEntity

@Dao
internal interface CachedVersionDao {

    @Query("SELECT * from versionTable")
    fun getVersion(): VersionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVersion(version: VersionEntity): Long
}