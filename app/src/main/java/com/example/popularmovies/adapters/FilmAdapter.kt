package com.example.popularmovies.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.R
import com.example.popularmovies.callbacks.DiffUtilCallBack
import com.example.popularmovies.callbacks.FilmCallback
import com.example.popularmovies.database.AppDatabase
import com.example.popularmovies.databinding.FilmItemBinding
import com.example.popularmovies.pojo.Film

class FilmAdapter(context: Context) :
    PagedListAdapter<Film, FilmAdapter.FilmViewHolder>(diffUtilCallback) {
    private lateinit var binding: FilmItemBinding
    private val genreDao = AppDatabase.getInstance(context).genreDao()
    var filmCallback: FilmCallback? = null

    class FilmViewHolder(val binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root)



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
                /*   for (id in film.genreIds) {
                       genresString = genresString + genreDao.getGenreById(id) + " "
                   }*/
                holder.binding.genre = genresString
                Log.d("GENRE STRING", genresString)
            }
        }
        holder.binding.executePendingBindings()
    }

    companion object {
        val diffUtilCallback = DiffUtilCallBack()
    }


}

