package com.leedsbeckett.todo_application.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * database class for accessing DAOs for each of the tables int he database, tables for accessing
 * attachments such as images will be added to this eventually as well
 */
@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object {
        @Volatile private var INSTANCE : TodoDatabase? = null

        fun getInstance(context : Context) : TodoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}