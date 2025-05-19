package es.rafapuig.movieapp.movies.ui.genres

import android.os.Bundle
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
import es.rafapuig.movieapp.databinding.ActivityGenresListBinding
import es.rafapuig.movieapp.movies.domain.model.Genre
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GenresListActivity : AppCompatActivity() {

    val binding: ActivityGenresListBinding by lazy {
        ActivityGenresListBinding.inflate(
            layoutInflater
        )
    }

    val viewModel: GenresListViewModel by viewModels { GenresListViewModel.Factory }

    val genreAdapter by lazy {
        GenreListAdapter { onGenreItemClick(it) }
    }


    fun onGenreItemClick(genre: Genre) {
        Snackbar
            .make(binding.root, "Clicked on ${genre.name}!!", Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLoad.setOnClickListener {
            viewModel.getGenres()
        }

        binding.rvGenres.adapter = genreAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->

                    binding.progressBar3.isVisible = uiState.isLoading

                    genreAdapter.submitList(uiState.genres)

                    if (uiState.errorMessage?.isNotEmpty() == true)
                        Snackbar.make(binding.root, uiState.errorMessage, Snackbar.LENGTH_LONG)
                            .show()
                }
            }
        }

    }


}