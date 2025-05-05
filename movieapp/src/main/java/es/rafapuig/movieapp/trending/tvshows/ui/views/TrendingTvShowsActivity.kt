package es.rafapuig.movieapp.trending.tvshows.ui.views

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
import es.rafapuig.movieapp.databinding.ActivityTrendingTvShowsBinding
import kotlinx.coroutines.launch

class TrendingTvShowsActivity : AppCompatActivity() {

    val binding by lazy { ActivityTrendingTvShowsBinding.inflate(layoutInflater) }

    val viewModel : TrendingTvShowsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trending_tv_shows)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.trendingTvShows.collect { uiState ->
                    binding.progressBar.isVisible = uiState.isLoading
                }
            }
        }

        binding.btnSwitchProgress.setOnClickListener {
            viewModel.switchProgressBarState()
        }


    }
}