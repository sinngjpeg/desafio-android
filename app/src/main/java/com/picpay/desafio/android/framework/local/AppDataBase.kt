package com.picpay.desafio.android.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.framework.dao.UserDAO
import com.picpay.desafio.android.framework.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}
