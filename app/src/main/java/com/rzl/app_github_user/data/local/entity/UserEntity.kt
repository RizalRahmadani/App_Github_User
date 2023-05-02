package com.rzl.app_github_user.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    var id: Int,

    @ColumnInfo
    var login: String?,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String?
)
