package com.example.aicollabcanvas.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aicollabcanvas.Profile

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(vararg profile: Profile)

    //Do we need this?
    @Delete
    fun deleteProfile(profile: Profile)

    //Why does this not work?
    //@Query("SELECT * FROM Post WHERE name=:name")
    fun getProfileByName(name: String)
}