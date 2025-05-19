package es.rafapuig.movieapp.movies.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import es.rafapuig.movieapp.databinding.GenreItemViewBinding
import es.rafapuig.movieapp.movies.domain.model.Genre

class GenreListAdapter(
    val onItemClickListener: (Genre) -> Unit = {}
) : ListAdapter<Genre, GenreViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        LayoutInflater.from(parent.context).let { layoutInflater ->
            GenreItemViewBinding.inflate(layoutInflater, parent, false)
        }.let { binding ->
            GenreViewHolder(binding).also { vh ->
                vh.itemView.setOnClickListener {
                    onItemClickListener(currentList[vh.absoluteAdapterPosition])
                }
            }
        }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        getItem(position).let { genre ->
            holder.bindTo(genre)
        }

}