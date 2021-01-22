package com.example.popularmovies.datasources

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.popularmovies.network.RetrofitInstance
import com.example.popularmovies.pojo.Film
import com.example.popularmovies.pojo.Page
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmDataSource() :
    PageKeyedDataSource<Int, Film>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Film>
    ) {
        RetrofitInstance.apiService.getPopular(page = 1).enqueue(object : Callback<Page> {
            override fun onResponse(call: Call<Page>, response: Response<Page>) {
                response.body()?.films?.let { callback.onResult(it, null,2) }
            }

            override fun onFailure(call: Call<Page>, t: Throwable) {
                Log.e("ERROR INITIAL", "some error $t")
            }
        })

    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Film>) {
        val page = if (params.key > 1) {
            params.key - 1
        } else 0
        RetrofitInstance.apiService.getPopular(page = page).enqueue(object : Callback<Page> {
            override fun onResponse(call: Call<Page>, response: Response<Page>) {
                response.body()?.films?.let { callback.onResult(it, page) }
            }

            override fun onFailure(call: Call<Page>, t: Throwable) {
                Log.e("ERROR BEFORE", "some error $t")
            }
        })
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Film>) {
        RetrofitInstance.apiService.getPopular(page = params.key)
            .enqueue(object : Callback<Page> {
                override fun onResponse(call: Call<Page>, response: Response<Page>) {
                    response.body()?.films?.let { callback.onResult(it, params.key + 1) }
                }

                override fun onFailure(call: Call<Page>, t: Throwable) {
                    Log.e("ERROR AFTER", "some error $t")
                }
            })
    }
}