package com.example.popularmovies.view.callbacks

import com.example.popularmovies.model.pojo.Film

interface FilmCallback {
    fun onClick(film: Film)
}