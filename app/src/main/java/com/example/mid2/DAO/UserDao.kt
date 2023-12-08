package com.example.mid2.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mid2.Model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEntity(users: List<UserEntity>)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}