package com.alexisdev.main

import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmCatalogFragment : Fragment() {
    private lateinit var binding: FragmentFilmCatalogBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var filmAdapter: FilmAdapter
    private val viewModel by viewModel<FilmCatalogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is FilmCatalogState.Loading -> { showProgress(true) }

                        is FilmCatalogState.Error -> { showErrorSnackbar(binding.root) }

                        is FilmCatalogState.Content -> {
                            showProgress(false)
                            binding.progressBar.visibility = View.GONE
                            renderContent(
                                genres = state.genres,
                                films = state.films,
                                selectedGenre = state.selectedGenre
                            )
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
                    val action =
                        FilmCatalogFragmentDirections.actionFilmCatalogFragmentToFilmDetailsNavGraph(
                            filmId
                        )
                    viewModel.onEvent(FilmCatalogEvent.OnFilmClick(action))
                }
            }
        )
        binding.rvGenres.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGenres.adapter = genreAdapter
        binding.rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFilms.adapter = filmAdapter
    }

    private fun renderContent(
        genres: List<GenreUi>,
        films: List<FilmUi>,
        selectedGenre: GenreUi?
    ) {
        binding.tvGenres.visibility = View.VISIBLE
        binding.tvFilms.visibility = View.VISIBLE
        genreAdapter.map(genres)
        filmAdapter.map(films)
        genreAdapter.updateSelectedGenre(selectedGenre)
    }

    private fun showProgress(isShow: Boolean) {
        if (isShow) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun showErrorSnackbar(view: View) {
        val snackbar = Snackbar.make(
            view,
            getString(R.string.film_catalog_snackbar_title), Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setActionTextColor(requireContext().getColor(com.alexisdev.designsystem.R.color.yellow))
        snackbar.setAction(getString(R.string.film_catalog_action_title)) {
            viewModel.onEvent(FilmCatalogEvent.OnRetry)
        }
        snackbar.show()
    }
}