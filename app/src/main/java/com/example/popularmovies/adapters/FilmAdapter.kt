package com.example.popularmovies.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.popularmovies.R
import com.example.popularmovies.callbacks.DiffUtilCallBack
import com.example.popularmovies.callbacks.FilmCallback
import com.example.popularmovies.database.AppDatabase
import com.example.popularmovies.databinding.FilmItemBinding
import com.example.popularmovies.network.RetrofitInstance
import com.example.popularmovies.pojo.Film

class FilmAdapter(context: Context) :
    PagedListAdapter<Film, FilmAdapter.FilmViewHolder?>(diffUtilCallback) {
    private lateinit var binding: FilmItemBinding
    private val genreDao = AppDatabase.getInstance(context).genreDao()
    var filmCallback: FilmCallback? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FilmViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context), R.layout.film_item,
            viewGroup, false
        )
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        film?.let {
            binding.film = film
            holder.itemView.setOnClickListener {
                filmCallback?.onClick(film)
            }
            Log.d("FILM IN ADAPTER", film.title.toString())
            film.genreIds?.let {
                var genresString: String = ""
                /*   for (id in film.genreIds) {
                       genresString = genresString + genreDao.getGenreById(id) + " "
                   }*/
                binding.genre = genresString
                Log.d("GENRE STRING", genresString)
            }

            film.posterPath?.let {
                val imagePath = RetrofitInstance.BASE_IMAGE_URL + film.posterPath
                Glide.with(holder.itemView.context)
                    .load(imagePath)
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                    )
                    .into(binding.imageFilm)
            }
        }
        binding.executePendingBindings()

    }

    companion object {
        val diffUtilCallback = DiffUtilCallBack()

    }

    inner class FilmViewHolder(binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


}

