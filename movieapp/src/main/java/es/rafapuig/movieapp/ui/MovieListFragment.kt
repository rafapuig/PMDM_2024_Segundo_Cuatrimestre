package es.rafapuig.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import es.rafapuig.movieapp.databinding.ActivityMainBinding
import es.rafapuig.movieapp.databinding.FragmentMovieListBinding
import es.rafapuig.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieAdapter by lazy { MoviePagingDataAdapter { onMovieItemClick(it) } }

    private val viewModel: MovieViewModel by viewModels { MovieViewModel.Factory }

    private fun onMovieItemClick(movie: Movie) {
        findNavController()
            .navigate(
                MovieListFragmentDirections
                    .actionMovieListFragmentToMovieDetailsFragment(movie.id)
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.adapter = movieAdapter

        lifecycleScope.launch {
            viewModel.fetchMoviesPaged().collectLatest {
                movieAdapter.submitData(it)
            }
        }

        /*binding.button.setOnClickListener {

            //findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment)
            findNavController().navigate(MovieListFragmentDirections
                .actionMovieListFragmentToMovieDetailsFragment(1064486))

            /*Navigation.findNavController(it)
                .navigate(R.id.action_movieListFragment_to_movieDetailsFragment)

            Navigation.createNavigateOnClickListener(
                R.id.action_movieListFragment_to_movieDetailsFragment, null
            )*/
        }*/
    }
}