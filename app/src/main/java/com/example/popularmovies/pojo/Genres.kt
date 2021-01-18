package com.example.popularmovies.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>?
)