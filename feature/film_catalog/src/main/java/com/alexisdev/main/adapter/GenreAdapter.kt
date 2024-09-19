package com.alexisdev.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexisdev.main.R
import com.alexisdev.designsystem.R as designsystem
import com.alexisdev.main.databinding.GenreCardItemBinding
import com.alexisdev.main.mapper.Mapper
import com.alexisdev.main.model.GenreUi

class GenreAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(), Mapper<List<GenreUi>> {
    private val list = mutableListOf<GenreUi>()
    private var selectedGenre: GenreUi? = null

    class GenreViewHolder(private val binding: GenreCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenreUi, isSelected: Boolean, clickListener: ClickListener) {
            val context = binding.root.context
            binding.root.setBackgroundColor(
                if (isSelected) context.getColor(designsystem.color.yellow)
                else context.getColor(designsystem.color.transparent)
            )
            binding.tvGenre.text = context.getString(genre.title)
        }
    }

    interface ClickListener {
        fun onClick(genre: GenreUi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            GenreCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(list[position], list[position] == selectedGenre, clickListener)
        holder.itemView.setOnClickListener {
            clickListener.onClick(list[position])
            updateSelectedGenre(list[position])
        }
    }

    fun updateSelectedGenre(genre: GenreUi?) {
        selectedGenre = genre
        notifyDataSetChanged()
    }

    override fun map(source: List<GenreUi>) {
        val diff = DiffUtil(list, source)
        val result = androidx.recyclerview.widget.DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }

    class DiffUtil(
        private val oldList: List<GenreUi>,
        private val newList: List<GenreUi>
    ) : androidx.recyclerview.widget.DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size


        override fun getNewListSize() = newList.size


        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}