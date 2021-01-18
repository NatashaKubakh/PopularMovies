package com.example.popularmovies.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.database.AppDatabase
import com.example.popularmovies.model.FilmDataSource
import com.example.popularmovies.network.RetrofitInstance
import com.example.popularmovies.pojo.Film
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FilmListViewModel(application: Application) : AndroidViewModel(application) {
    private var filmLiveData: LiveData<PagedList<Film>>
    private lateinit var db: AppDatabase
    private var compositeDisposable: CompositeDisposable


    init {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(7)
            .build()
        filmLiveData = initializedPagedListBuilder(config).build()
        db = AppDatabase.getInstance(application)
        compositeDisposable = CompositeDisposable()
    }

    fun getResults(): LiveData<PagedList<Film>> = filmLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Film> {
        val dataSourceFactory = object : DataSource.Factory<Int, Film>() {
            override fun create(): DataSource<Int, Film> {
                return FilmDataSource()
            }
        }
        return LivePagedListBuilder<Int, Film>(dataSourceFactory, config)
    }

    fun loadGenreData() {
        val disposable = RetrofitInstance.apiService.getGenre()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let { it.genres?.let { it1 -> db.genreDao().insertGenreList(it1) } }
            }, {
                it.message?.let { it1 -> Log.d("TEST_OF_LOADING_DATA", it1) }
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}