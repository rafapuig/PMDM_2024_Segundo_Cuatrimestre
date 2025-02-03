package es.rafapuig.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.rafapuig.movieapp.MovieApplication
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.domain.MovieRepository
import es.rafapuig.movieapp.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    /*private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> get() = _movies*/

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> get() = _movies

    /*private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> get() = _loading*/

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    /* fun fetchMovies() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _movies.postValue(movieRepository.fetchMovies())
            _loading.postValue(false)
        }
    } */

    fun fetchMovies() {
        //_loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMoviesFlow()
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = "Se ha producido un error ${it.message}"
                }
                .collect {
                    _movies.value = it
                    //_loading.value = false
                }
            //movieRepository.fetchMoviesFlow().asLiveData()
            //_loading.value = false
        }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                MovieViewModel(application.movieRepository)
            }
        }
    }

}