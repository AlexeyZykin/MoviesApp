package com.alexisdev.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexisdev.main.databinding.FilmCardItemBinding
import com.alexisdev.main.mapper.Mapper
import com.alexisdev.main.model.FilmUi
import com.alexisdev.designsystem.R as designsystem
import com.bumptech.glide.Glide

class FilmAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>(), Mapper<List<FilmUi>> {
    private val list = mutableListOf<FilmUi>()

    class FilmViewHolder(private val binding: FilmCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmUi, clickListener: ClickListener) {
            binding.tvFilmTitle.text = film.localizedName
            Glide
                .with(binding.root)
                .load(film.imageUrl)
                .placeholder(designsystem.drawable.film_placeholder)
                .centerCrop()
                .into(binding.imgFilm)
            binding.root.setOnClickListener { clickListener.onClick(film.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding =
            FilmCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    override fun map(source: List<FilmUi>) {
        val diff = DiffUtil(list, source)
        val result = androidx.recyclerview.widget.DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }

    class DiffUtil(
        private val oldList: List<FilmUi>,
        private val newList: List<FilmUi>
    ) : androidx.recyclerview.widget.DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    interface ClickListener {
        fun onClick(filmId: Int)
    }
}