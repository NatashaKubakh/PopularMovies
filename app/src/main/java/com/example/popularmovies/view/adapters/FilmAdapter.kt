package com.example.popularmovies.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.view.callbacks.DiffUtilCallBack
import com.example.popularmovies.view.callbacks.FilmCallback
import com.example.popularmovies.model.database.AppDatabase
import com.example.popularmovies.databinding.FilmItemBinding
import com.example.popularmovies.model.pojo.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmAdapter(context: Context) :
    PagedListAdapter<Film, FilmAdapter.FilmViewHolder>(diffUtilCallback) {
    private lateinit var binding: FilmItemBinding
    private val genreDao = AppDatabase.getInstance(context).genreDao()
    var filmCallback: FilmCallback? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    class FilmViewHolder(binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: FilmItemBinding = binding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.film_item,
            parent, false
        )
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film: Film? = getItem(position)
        film?.let {
            holder.binding.film = film
            Log.d("FILM IN ADAPTER", film.title.toString())
            holder.binding.itemFilm.setOnClickListener {
                filmCallback?.onClick(film)
            }
            film.genreIds?.let {
                var genresString: String = ""
                coroutineScope.launch {
                    for (id in film.genreIds) {
                        genresString = genresString + genreDao.getGenreById(id) + " "
                    }
                    withContext(Dispatchers.Main) {
                        Log.d("GENRE STRING", genresString)
                        holder.binding.tvGenre.text = genresString
                    }
                }
            }
        }
        holder.binding.executePendingBindings()
    }

    companion object {
        val diffUtilCallback = DiffUtilCallBack()
    }
}

