package es.rafapuig.movieapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import es.rafapuig.movieapp.R
import es.rafapuig.movieapp.databinding.ViewMovieItemBinding
import es.rafapuig.movieapp.domain.model.Movie

class MovieViewHolder(private val binding: ViewMovieItemBinding) : ViewHolder(binding.root) {

    private val imageWidth = 185

    private val imageUrl = "https://image.tmdb.org/t/p/w$imageWidth/"

    /*private val titleText: TextView by lazy {
        //itemView.findViewById(R.id.movie_title)
        binding.movieTitle
    }*/

    private val poster: ImageView by lazy {
        itemView.findViewById(R.id.movie_poster)
    }

    fun bindTo(movie: Movie) {
        binding.movieTitle.text = movie.title

        binding.moviePoster.setImageResource(R.mipmap.ic_launcher)

        if (movie.posterPath.isNotBlank()) {
            Glide.with(itemView.context)
                .load("$imageUrl${movie.posterPath}")
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                //.circleCrop()
                .into(poster)
        }

    }


}