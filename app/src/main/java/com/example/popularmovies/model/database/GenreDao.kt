package com.example.popularmovies.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.popularmovies.model.pojo.Genre


@Dao
interface GenreDao {

    @Query("SELECT * FROM genre_list")
    fun getGenreList(): LiveData<List<Genre>>

    @Query("SELECT name FROM genre_list WHERE id ==:id")
    fun getGenreById(id: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreList(genreList: List<Genre>)

}