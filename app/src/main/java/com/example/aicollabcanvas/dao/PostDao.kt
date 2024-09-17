package com.example.aicollabcanvas.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aicollabcanvas.Post


@Dao
interface PostDao {

    @Query("SELECT * FROM Post")
    fun getAllPosts():List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(vararg posts: Post)

    @Delete
    fun deletePost(post: Post)

    //Do this for post based on some id - maybe add field post id
    //@Query("SELECT * FROM Post WHERE id=:id")
    //fun getPostById(id: String)


}