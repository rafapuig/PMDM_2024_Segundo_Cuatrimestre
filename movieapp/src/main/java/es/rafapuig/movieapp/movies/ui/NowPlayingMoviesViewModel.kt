package es.rafapuig.movieapp.movies.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.movies.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NowPlayingMoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NowPlayingMoviesUiState>(NowPlayingMoviesUiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun fetchMovies() {
        //_loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMoviesFlow()
                .onStart { _uiState.value = NowPlayingMoviesUiState.Loading }
                //.onCompletion { _uiState.value }
                .catch {
                    Log.i("RAFA","Error: ${it.message}")
                    _uiState.value =
                        NowPlayingMoviesUiState.Error("Se ha producido un error ${it.message}")
                }
                .collect {
                    Log.i("RAFA","$it")
                    _uiState.value = NowPlayingMoviesUiState.Success(it)
                    //_loading.value = false
                }
            //movieRepository.fetchMoviesFlow().asLiveData()
            //_loading.value = false
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TheMovieDBApplication)
                NowPlayingMoviesViewModel(application.moviesRepository)
            }
        }
    }

}