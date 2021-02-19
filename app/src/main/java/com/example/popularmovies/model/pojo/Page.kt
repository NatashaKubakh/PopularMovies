package com.example.popularmovies.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Page(
    @SerializedName("page")
    @Expose
    val page: Int?,
    @SerializedName("results")
    @Expose
    val films: List<Film>?
)
