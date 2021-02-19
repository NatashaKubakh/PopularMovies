package com.example.popularmovies.model.network

import com.example.popularmovies.model.pojo.Genres
import com.example.popularmovies.model.pojo.Page
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopular(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_LANGUAGE) language: String = LANGUAGE,
        @Query(QUERY_PARAM_PAGE) page: Int,
        @Query(QUERY_PARAM_REGION) region: String = REGION
    ): Call<Page>

    @GET("genre/movie/list")
    fun getGenre(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_LANGUAGE) language: String = LANGUAGE,
    ): Single<Genres>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_LANGUAGE = "language"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_REGION = "region"


        private const val API_KEY = "c95a75af7a2ca4dd32da047bac98a2fc"
        private const val LANGUAGE = "en-US"
        private const val REGION = "US"


    }
}