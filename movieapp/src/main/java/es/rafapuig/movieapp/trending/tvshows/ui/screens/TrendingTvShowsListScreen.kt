package es.rafapuig.movieapp.trending.tvshows.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import es.rafapuig.movieapp.trending.tvshows.ui.TrendingTvShowsViewModel
import es.rafapuig.movieapp.trending.tvshows.ui.TrendingTvShowsViewModel.UiState

@Composable
fun TrendingTvShowsListScreen(
    modifier: Modifier,
    viewModel: TrendingTvShowsViewModel = viewModel(factory = TrendingTvShowsViewModel.Factory)
) {
    val uiState by viewModel.trendingTvShows.collectAsState()

    Box(modifier = modifier) {
        when (uiState) {
            UiState.Loading -> Text("Cargando...")
            is UiState.Error -> Text((uiState as UiState.Error).exception.message.toString())
            is UiState.Success -> Text((uiState as UiState.Success).tvShows.toString())
        }
    }
}