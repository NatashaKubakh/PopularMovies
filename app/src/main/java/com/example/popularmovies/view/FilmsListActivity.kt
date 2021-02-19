package com.example.popularmovies.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies.R
import com.example.popularmovies.view.adapters.FilmAdapter
import com.example.popularmovies.view.callbacks.FilmCallback
import com.example.popularmovies.databinding.ActivityFilmListBinding
import com.example.popularmovies.model.pojo.Film
import com.example.popularmovies.viewModel.FilmListViewModel

class FilmsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding
    private lateinit var viewModel: FilmListViewModel
    private lateinit var adapter: FilmAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_film_list)
        adapter = FilmAdapter(this)
        binding.filmsList.adapter = adapter

        viewModel = ViewModelProvider.AndroidViewModelFactory(this.application)
            .create(FilmListViewModel::class.java)
        viewModel.loadGenreData()
        viewModel.getFilmsPagedLiveData().observe(this, Observer {
            adapter.submitList(it)
        })
        adapter.filmCallback = object : FilmCallback {
            override fun onClick(film: Film) {
                val intent = SingleFilmActivity.newIntent(
                    this@FilmsListActivity, film
                )
                startActivity(intent)
            }
        }

    }
}