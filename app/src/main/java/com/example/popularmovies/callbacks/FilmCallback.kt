package com.example.popularmovies.callbacks

import com.example.popularmovies.pojo.Film

interface FilmCallback {
    fun onClick(film: Film)
}