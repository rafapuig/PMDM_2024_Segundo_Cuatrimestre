package es.rafapuig.movieapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import es.rafapuig.movieapp.databinding.ViewMovieItemBinding
import es.rafapuig.movieapp.movies.domain.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private val differ = AsyncListDiffer(this, MovieListAdapter.DIFF_CALLBACK)

    //private val movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewMovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position] //movies[position]
        holder.bindTo(movie)
    }


    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(movies: List<Movie>) {
        differ.submitList(movies)
    }


    /*fun addMovies(movieList: List<Movie>) {
        val oldSize = itemCount
        movies.clear()
        notifyItemRangeRemoved(0, oldSize)

        movies.addAll(movieList)
        notifyItemRangeInserted(0, movieList.size)
    }*/


}

