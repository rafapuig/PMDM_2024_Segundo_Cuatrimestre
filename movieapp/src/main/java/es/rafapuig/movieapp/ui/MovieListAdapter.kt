package es.rafapuig.movieapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import es.rafapuig.movieapp.databinding.ViewMovieItemBinding
import es.rafapuig.movieapp.domain.model.Movie

class MovieListAdapter : ListAdapter<Movie, MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).let { layoutInflater ->
            ViewMovieItemBinding.inflate(layoutInflater, parent, false)
        }.let { binding ->
            MovieViewHolder(binding)
        }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindTo(getItem(position))


}