package es.rafapuig.movieapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.rafapuig.movieapp.R
import es.rafapuig.movieapp.data.network.model.MovieResponse
import es.rafapuig.movieapp.databinding.ViewMovieItemBinding
import es.rafapuig.movieapp.domain.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewMovieItemBinding.inflate(layoutInflater)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }


    override fun getItemCount(): Int = movies.size


    fun addMovies(movieList: List<Movie>) {
        movies.addAll(movieList)
        notifyItemRangeInserted(0, movieList.size)
    }


    class MovieViewHolder(private val binding: ViewMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val imageUrl = "https://image.tmdb.org/t/p/w185/"

        /*private val titleText: TextView by lazy {
            //itemView.findViewById(R.id.movie_title)
            binding.movieTitle
        }*/

        private val poster: ImageView by lazy {
            itemView.findViewById(R.id.movie_poster)
        }


        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title

            Glide.with(itemView.context)
                .load("$imageUrl${movie.posterPath}")
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(poster)
        }
    }
}

