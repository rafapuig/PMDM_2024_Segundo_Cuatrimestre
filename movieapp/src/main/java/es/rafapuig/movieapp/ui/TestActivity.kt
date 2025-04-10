package es.rafapuig.movieapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import es.rafapuig.movieapp.R
import es.rafapuig.movieapp.databinding.ActivityTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }

    private val viewModel: TvShowsViewModel by viewModels { TvShowsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        initUiStateObserving()

        viewModel.fetchTrendingTvShows()
    }

    private fun initUiStateObserving() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trendingTvShows.collectLatest { uiState ->
                    when (uiState) {
                        TvShowsUiState.Loading -> binding.messageTextView.text = "Cargando..."
                        is TvShowsUiState.Success ->
                            binding.messageTextView.text = uiState.tvShows.toString()

                        is TvShowsUiState.Error ->
                            binding.messageTextView.text = uiState.exception.message.toString()


                    }
                }
            }
        }
    }

}