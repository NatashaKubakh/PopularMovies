package com.example.popularmovies

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.popularmovies.databinding.ActivitySingleFilmBinding
import com.example.popularmovies.network.RetrofitInstance
import com.example.popularmovies.pojo.Film
import com.example.popularmovies.viewModel.FilmListViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

class SingleFilmActivity : AppCompatActivity() {
    private lateinit var viewModel: FilmListViewModel
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