package com.example.popularmovies.model.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.popularmovies.model.pojo.Film

class FilmDataSourceFactory() : DataSource.Factory<Int, Film>() {

    private var mutableLiveData = MutableLiveData<FilmDataSource>()

    override fun create(): DataSource<Int, Film> {
        val filmDataSource = FilmDataSource()
        mutableLiveData.postValue(filmDataSource)
        return filmDataSource
    }

    fun getMutableLiveData(): MutableLiveData<FilmDataSource> {
        mutableLiveData?.let { return it }
    }
}