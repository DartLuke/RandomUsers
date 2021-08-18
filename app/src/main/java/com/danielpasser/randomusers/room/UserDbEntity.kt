package com.danielpasser.randomusers.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDbEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "email")
    var email: String?,

//name
    @ColumnInfo(name = "first")
    val first: String?,
    @ColumnInfo(name = "last")
    val last: String?,

    //picture
    @ColumnInfo(name = "large")
    var large: String?,
    @ColumnInfo(name = "medium")
    var medium: String?,
    @ColumnInfo(name = "thumbnail")
    var thumbnail: String?,


    @ColumnInfo(name = "dob")
    val date: String?
)

