package es.rafapuig.movieapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import es.rafapuig.movieapp.databinding.ViewMovieItemBinding
import es.rafapuig.movieapp.domain.model.Movie

class MoviePagingDataAdapter(
    val onItemClickListener: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieViewHolder>(MovieComparator) {

    companion object {
        val MovieComparator = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(old: Movie, new: Movie) = old.id == new.id
            override fun areContentsTheSame(old: Movie, new: Movie) = old == new
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).let { layoutInflater ->
            ViewMovieItemBinding.inflate(layoutInflater, parent, false)
        }.let { binding ->
            MovieViewHolder(binding).also { vh ->
                vh.itemView.setOnClickListener {
                    getItem(vh.absoluteAdapterPosition)?.let { movie ->
                        onItemClickListener(movie)
                    }
                }
            }
        }
}