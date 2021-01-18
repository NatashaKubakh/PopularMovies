package com.example.popularmovies.model

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.network.RetrofitInstance
import com.example.popularmovies.pojo.Film

class FilmDataSource :
    PageKeyedDataSource<Int, Film>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Film>
    ) {
        try {
            val response = RetrofitInstance.apiService.getPopular(page = 1).execute()
            when {
                response.isSuccessful -> {
                    val results = response.body()?.films
                    results?.let {
                        callback.onResult(results, 0, 2)
                    }
                }
            }
        } catch (exception: Exception) {
            Log.d("ERROR", "loadInitial")
        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Film>) {
        if (params.key > 1) {
        }
        try {
            val response = RetrofitInstance.apiService.getPopular(page = params.key - 1).execute()
            when {
                response.isSuccessful -> {
                    val results = response.body()?.films
                    results?.let {
                        callback.onResult(results, params.key - 1)
                    }
                }
            }
        } catch (exception: Exception) {
            Log.d("ERROR", "loadBefore")
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Film>) {

        try {
            val response = RetrofitInstance.apiService.getPopular(page = params.key + 1).execute()
            when {
                response.isSuccessful -> {
                    val results = response.body()?.films
                    results?.let {
                        callback.onResult(results, params.key + 1)
                    }
                }
            }
        } catch (exception: Exception) {
            Log.d("ERROR", "loadAfter")
        }
    }

    override fun invalidate() {
        super.invalidate()
    }
}