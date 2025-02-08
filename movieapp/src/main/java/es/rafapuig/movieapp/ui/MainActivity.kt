package es.rafapuig.movieapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import es.rafapuig.movieapp.databinding.ActivityMainBinding
import es.rafapuig.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityMainBinding

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //private val movieAdapter by lazy { MovieListAdapter() }

    private val movieAdapter by lazy { MoviePagingDataAdapter { onMovieItemClick(it) } }

    private val viewModel: MovieViewModel by viewModels { MovieViewModel.Factory }

    fun onMovieItemClick(movie: Movie) {
        Snackbar
            .make(binding.root, "Clicked on ${movie.title}!!", Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.movieList.adapter = movieAdapter

        //viewModel.fetchMovies()
        //viewModel.fetchMoviesPaged()

        /*viewModel.movies.observe(this) { movies ->
            movieAdapter.addMovies(movies)
        }

        viewModel.loading.observe(this) { loading ->
            binding.progressBar.isVisible = loading
        }*/

        /*lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movies.collect { movies ->
                        //movieAdapter.addMovies(movies)
                        movieAdapter.submitList(movies)
                    }
                }
                launch {
                    viewModel.error.collect { error ->
                        if(error.isNotEmpty())
                            Snackbar.make(binding.movieList, error, Snackbar.LENGTH_LONG).show()
                    }
                }
                launch {
                    viewModel.loading.collect { loading ->
                        binding.progressBar.isVisible = loading
                    }
                }
            }
        }*/


        lifecycleScope.launch {
            viewModel.fetchMoviesPaged().collectLatest {
                movieAdapter.submitData(it)
            }
        }

    }
}