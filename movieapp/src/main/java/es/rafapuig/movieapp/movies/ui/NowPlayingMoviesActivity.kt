package es.rafapuig.movieapp.movies.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import es.rafapuig.movieapp.R
import es.rafapuig.movieapp.databinding.ActivityNowPlayingMoviesBinding
import es.rafapuig.movieapp.movies.domain.model.Movie
import es.rafapuig.movieapp.ui.MovieAdapter
import es.rafapuig.movieapp.ui.MovieListAdapter
import es.rafapuig.movieapp.ui.MoviePagingDataAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingMoviesActivity : AppCompatActivity() {

    private val viewModel by viewModels<NowPlayingMoviesViewModel> {
        NowPlayingMoviesViewModel.Factory
    }

    private val binding by lazy { ActivityNowPlayingMoviesBinding.inflate(layoutInflater) }

    private val movieAdapter by lazy { MovieListAdapter { onMovieItemClick(it) } }

    private fun onMovieItemClick(movie: Movie) {
        Snackbar
            .make(binding.root, "Clicked on ${movie.title}!!", Snackbar.LENGTH_SHORT)
            .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.movieList.adapter = movieAdapter

        viewModel.fetchMovies()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest{ uiState ->

                    binding.progressBar.isVisible = false

                    when(uiState) {
                        NowPlayingMoviesUiState.Empty -> Unit

                        is NowPlayingMoviesUiState.Error -> Snackbar
                            .make(binding.root, uiState.message, Snackbar.LENGTH_SHORT).show()

                        NowPlayingMoviesUiState.Loading ->
                            binding.progressBar.isVisible = true

                        is NowPlayingMoviesUiState.Success ->
                            movieAdapter.submitList(uiState.movies)
                    }
                }
            }
        }


    }
}