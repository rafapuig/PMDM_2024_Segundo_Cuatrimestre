package es.rafapuig.movieapp.movies.ui.genres

import androidx.recyclerview.widget.RecyclerView
import es.rafapuig.movieapp.databinding.GenreItemViewBinding
import es.rafapuig.movieapp.movies.domain.model.Genre

class GenreViewHolder(
    private val binding: GenreItemViewBinding,
    //private val onItemClickListener: (Genre) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(genre: Genre) {

        binding.tvName.text = genre.name
        /*binding.root.setOnClickListener {
            onItemClickListener(genre)
        }*/
    }

}