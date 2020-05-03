package com.pedroroig.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pedroroig.cache.db.ProjectConstants.COLUMN_IS_BOOKMARKED
import com.pedroroig.cache.db.ProjectConstants.COLUMN_PROJECT_ID
import com.pedroroig.cache.db.ProjectConstants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CachedProject(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_PROJECT_ID)
    var id: String,
    val name: String,
    val fullName: String,
    val starCount: String,
    val dateCreated: String,
    val ownerName: String,
    val ownerAvatar: String,
    @ColumnInfo(name = COLUMN_IS_BOOKMARKED)
    val isBookmarked: Boolean
)