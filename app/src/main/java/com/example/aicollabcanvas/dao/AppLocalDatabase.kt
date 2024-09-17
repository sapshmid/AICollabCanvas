package com.example.aicollabcanvas.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aicollabcanvas.Post
import com.example.aicollabcanvas.Profile
import com.example.aicollabcanvas.base.MyApplication

@Database(entities = [Post::class, Profile::class], version = 1)
abstract class AppLocalDbRepository : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun profileDao(): ProfileDao
}
object AppLocalDatabase {

    val db: AppLocalDbRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}


