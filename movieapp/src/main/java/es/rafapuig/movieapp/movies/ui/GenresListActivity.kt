package es.rafapuig.movieapp.movies.ui

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
import es.rafapuig.movieapp.R
import es.rafapuig.movieapp.databinding.ActivityGenresListBinding
import kotlinx.coroutines.launch

class GenresListActivity : AppCompatActivity() {

    val binding: ActivityGenresListBinding by lazy { ActivityGenresListBinding.inflate(layoutInflater) }

    val viewModel: GenresListViewModel by viewModels { GenresListViewModel.Factory }

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.progressBar3.isVisible = uiState.isLoading
                }
            }
        }

    }


}