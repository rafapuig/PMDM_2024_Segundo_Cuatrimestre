package es.rafapuig.movieapp.trending.tvshows.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import es.rafapuig.movieapp.ui.ui.theme.PMDM_2024_Segundo_CuatrimestreTheme

class TrendingTvShowsListActivity : ComponentActivity() {

    //val viewModel: TvShowsViewModel by viewModels { TvShowsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PMDM_2024_Segundo_CuatrimestreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrendingTvShowsListScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TrendingTvShowsListScreen(
    modifier: Modifier,
    viewModel: TvShowsViewModel = viewModel(factory = TvShowsViewModel.Factory)
) {
    val uiState by viewModel.trendingTvShows.collectAsState()

    Box(modifier = modifier) {
        when (uiState) {
            TvShowsUiState.Loading -> Text("Cargando...")
            is TvShowsUiState.Error -> Text((uiState as TvShowsUiState.Error).exception.message.toString())
            is TvShowsUiState.Success -> Text((uiState as TvShowsUiState.Success).tvShows.toString())
        }
    }
}