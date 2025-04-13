package es.rafapuig.movieapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import es.rafapuig.movieapp.databinding.FragmentMovieDetailsBinding
import kotlinx.coroutines.launch

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMovieDetails(args.movieId)



        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.progressBar.isVisible = false
                    when (uiState) {
                        MovieDetailsUiState.Loading -> binding.progressBar.isVisible = true

                        is MovieDetailsUiState.Error -> {}

                        is MovieDetailsUiState.Success -> bindData(uiState)
                    }
                }
            }
        }
    }

    private fun bindData(uiState: MovieDetailsUiState.Success) {
        binding.apply {
            with(uiState.movie) {
                movieTitle.text = title

                val url = "https://www.themoviedb.org/movie/796-cruel-intentions/watch?locale=ES" //"https://www.youtube.com/watch?v=${uiState.videoKey}"



                watchTrailer.setOnClickListener {
                    Intent(Intent.ACTION_VIEW)
                        .apply { data = Uri.parse(url) }
                        .also { startActivity(it) }

                    //Snackbar.make(root, url, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }


}