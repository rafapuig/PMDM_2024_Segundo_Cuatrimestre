package es.rafapuig.movieapp.movies.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import es.rafapuig.movieapp.R
import es.rafapuig.movieapp.databinding.ActivityNowPlayingMoviesBinding
import es.rafapuig.movieapp.ui.MovieListAdapter

class NowPlayingMoviesDirectlyFromApiActivity : AppCompatActivity() {

    private val viewModel by viewModels<NowPlayingMoviesDirectlyFromApiViewModel> {
        NowPlayingMoviesDirectlyFromApiViewModel.Factory
    }

    val binding by lazy { ActivityNowPlayingMoviesBinding.inflate(layoutInflater) }

    private val movieAdapter by lazy { MovieListAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.movieList.adapter = movieAdapter

        //viewModel.fetchMoviesDirectlyFromApiService()

        viewModel.state.observe(this) { state->
            when {
                state.isLoading -> binding.progressBar.isVisible = true
                state.moviesList.isNotEmpty() -> {
                    binding.progressBar.isVisible = false
                    movieAdapter.submitList(state.moviesList)
                }
                state.error != null -> {
                    binding.progressBar.isVisible = false
                    Snackbar.make(this, binding.root, state.error, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}