package com.example.popularmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies.adapters.FilmAdapter
import com.example.popularmovies.callbacks.FilmCallback
import com.example.popularmovies.databinding.ActivityFilmListBinding
import com.example.popularmovies.pojo.Film
import com.example.popularmovies.viewModel.FilmListViewModel

class FilmsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding
    private lateinit var viewModel: FilmListViewModel
    private lateinit var adapter: FilmAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_film_list)
        adapter = FilmAdapter()
        binding.filmsList.adapter = adapter
        adapter.filmCallback = object : FilmCallback {
            override fun onClick(film: Film) {
                val intent = SingleFilmActivity.newIntent(
                    this@FilmsListActivity, film
                )
                startActivity(intent)
            }
        }
        viewModel = ViewModelProvider.AndroidViewModelFactory(this.application)
            .create(FilmListViewModel::class.java)
        viewModel.loadGenreData()
        viewModel.getResults().observe(this, Observer {
            adapter.filmsList = it
        })
    }
}