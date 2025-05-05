package es.rafapuig.movieapp.trending.tvshows.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import es.rafapuig.movieapp.trending.tvshows.ui.screens.TrendingTvShowsListScreen
import es.rafapuig.movieapp.ui.ui.theme.PMDM_2024_Segundo_CuatrimestreTheme

class TrendingTvShowsListActivity : ComponentActivity() {

    //private val viewModel: TrendingTvShowsViewModel by viewModels { TrendingTvShowsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PMDM_2024_Segundo_CuatrimestreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrendingTvShowsListScreen(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

