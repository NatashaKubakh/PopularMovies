package com.example.popularmovies.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.popularmovies.pojo.Film

class DiffUtilCallBack : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem

    }
}