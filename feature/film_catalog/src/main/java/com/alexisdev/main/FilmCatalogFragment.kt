package com.alexisdev.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisdev.main.adapter.FilmAdapter
import com.alexisdev.main.adapter.GenreAdapter
import com.alexisdev.main.databinding.FragmentFilmCatalogBinding
import com.alexisdev.main.model.FilmUi
import com.alexisdev.main.model.GenreUi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmCatalogFragment : Fragment() {
    //private var binding: FragmentFilmCatalogBinding? = null
    private lateinit var binding: FragmentFilmCatalogBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var filmAdapter: FilmAdapter
    private val viewModel by viewModel<FilmCatalogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilmCatalogBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is FilmCatalogState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is FilmCatalogState.Error -> {  }
                        is FilmCatalogState.Content -> {
                            binding.progressBar.visibility = View.GONE
                            showContent(genres = state.genres, films = state.films)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        genreAdapter = GenreAdapter(
            object : GenreAdapter.ClickListener {
                override fun onClick(genre: GenreUi) {
                    viewModel.onEvent(FilmCatalogEvent.OnSelectGenre(genre))
                }
            }
        )
        filmAdapter = FilmAdapter(
            object : FilmAdapter.ClickListener {
                override fun onClick(filmId: Int) {
                    viewModel.onEvent(FilmCatalogEvent.OnFilmClick(filmId))
                }
            }
        )
        binding.rvGenres.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGenres.adapter = genreAdapter
        binding.rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFilms.adapter = filmAdapter
    }

    private fun showContent(
        genres: List<GenreUi>,
        films: List<FilmUi>
    ) {
        binding.tvGenres.visibility = View.VISIBLE
        binding.tvFilms.visibility = View.VISIBLE
        genreAdapter.map(genres)
        filmAdapter.map(films)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
}