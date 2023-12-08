package com.example.mid2.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mid2.DAO.UserDao
import com.example.mid2.Model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}