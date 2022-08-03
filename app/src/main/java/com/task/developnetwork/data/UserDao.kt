package com.task.developnetwork.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Query("SELECT status FROM users WHERE email = :userEmail")
    fun getUserStatus(userEmail: String): String

    @Query("SELECT password FROM users WHERE phone = :userPhone")
    suspend fun getUserPassword(userPhone: String): String

    @Query("SELECT userToken FROM users WHERE email = :userEmail")
    fun getUserToken(userEmail: String): String


    @Query("UPDATE users SET status=:status WHERE email = :userEmail")
    fun updateUserStatus(userEmail: String, status: String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(user: User)

}