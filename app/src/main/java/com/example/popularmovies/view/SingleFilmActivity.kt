package com.example.popularmovies.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.popularmovies.R
import com.example.popularmovies.databinding.ActivitySingleFilmBinding
import com.example.popularmovies.model.pojo.Film

class SingleFilmActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_film)
        if (!intent.hasExtra(FILM_RESULT)) {
            finish()
            return
        }
        val film = intent.getSerializableExtra(FILM_RESULT) as Film
        binding.film = film
    }

    companion object {
        private const val FILM_RESULT = "film_result"

        fun newIntent(context: Context, film: Film): Intent {
            val intent = Intent(context, SingleFilmActivity::class.java)
            film?.let { intent.putExtra(FILM_RESULT, film) }
            return intent
        }
    }
}