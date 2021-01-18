package com.example.popularmovies.adapters

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
import com.example.popularmovies.databinding.FilmItemBinding
import com.example.popularmovies.network.RetrofitInstance
import com.example.popularmovies.pojo.Film

class FilmAdapter() : PagedListAdapter<Film, FilmAdapter.FilmViewHolder?>(diffUtilCallback) {
    private lateinit var binding: FilmItemBinding
    var filmCallback: FilmCallback? = null
    var filmsList: List<Film> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.film_item,
            parent, false
        )
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        with(holder) {
            binding.film = film
            film?.let {
                itemView.setOnClickListener {
                    filmCallback?.onClick(film)
                }
                film.posterPath?.let {
                    val imagePath = RetrofitInstance.BASE_IMAGE_URL + film.posterPath
                    Glide.with(holder.itemView.context)
                        .load(imagePath)
                        .apply(
                            RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true))
                        .into(binding.layoutMain)
                }
            }
        }
    }

    companion object {
        val diffUtilCallback = DiffUtilCallBack()

    }

    inner class FilmViewHolder(binding: FilmItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


}