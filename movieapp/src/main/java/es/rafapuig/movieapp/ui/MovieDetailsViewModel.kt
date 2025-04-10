package es.rafapuig.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.movieapp.TheMovieDBApplication
import es.rafapuig.movieapp.data.network.model.MovieDetailsResponse
import es.rafapuig.movieapp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> get() = _uiState


    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = movieRepository.fetchMovieDetails(movieId)


            movie?.let {
                val videoKey = movieRepository.fetchMovieVideos(it.id)?.results?.let {
                    it.filter { video ->
                        video.type == "Trailer"
                    }.map { video -> video.key }.firstOrNull()
                }

                _uiState.value = MovieDetailsUiState.Success(it, videoKey)
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TheMovieDBApplication
                MovieDetailsViewModel(application.movieRepository)
            }
        }
    }
}

sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Success(val movie: MovieDetailsResponse, val videoKey: String?) :
        MovieDetailsUiState()

    data class Error(val exception: Throwable) : MovieDetailsUiState()
}