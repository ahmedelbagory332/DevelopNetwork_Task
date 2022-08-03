package com.task.developnetwork.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int?,
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val status: String,
    val userToken: String,
)