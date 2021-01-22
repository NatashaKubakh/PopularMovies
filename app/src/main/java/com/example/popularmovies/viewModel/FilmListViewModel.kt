package com.example.popularmovies.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.popularmovies.database.AppDatabase
import com.example.popularmovies.datasources.FilmDataSource
import com.example.popularmovies.datasources.FilmDataSourceFactory
import com.example.popularmovies.network.RetrofitInstance
import com.example.popularmovies.pojo.Film
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

class FilmListViewModel(application: Application) : AndroidViewModel(application) {
    private var filmLiveData: MutableLiveData<FilmDataSource>
    private var filmPagedLiveData: LiveData<PagedList<Film>> = MutableLiveData<PagedList<Film>>()
    private var db: AppDatabase
    private var compositeDisposable: CompositeDisposable


    init {
        val factory: FilmDataSourceFactory by lazy {
            FilmDataSourceFactory()
        }
        filmLiveData = factory.getMutableLiveData()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setPageSize(PAGE_SIZE)
            .build()
        val executor = Executors.newFixedThreadPool(5)
        filmPagedLiveData = LivePagedListBuilder(factory, config)
            .setFetchExecutor(executor)
            .build()
        db = AppDatabase.getInstance(application)
        compositeDisposable = CompositeDisposable()
    }

    fun getFilmsPagedLiveData(): LiveData<PagedList<Film>> = filmPagedLiveData


    fun loadGenreData() {
        val disposable = RetrofitInstance.apiService.getGenre()
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let {
                    it.genres?.let { db.genreDao().insertGenreList(it) }
                    Log.d("GENRE DATA", it.toString())
                }
            }, {
                it.message?.let { it1 -> Log.d("TEST_OF_LOADING_GENRES", it1) }
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}