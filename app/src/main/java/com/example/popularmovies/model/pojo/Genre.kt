package com.example.popularmovies.model.pojo

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genre_list")
data class Genre(
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Integer,
    @SerializedName("name")
    @Expose
    val name: String?
)