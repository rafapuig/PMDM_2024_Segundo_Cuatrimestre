package es.rafapuig.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import es.rafapuig.movieapp.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

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

        binding.button.setOnClickListener {

            //findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment)
            findNavController().navigate(MovieListFragmentDirections
                .actionMovieListFragmentToMovieDetailsFragment("Rafa Puig"))

            /*Navigation.findNavController(it)
                .navigate(R.id.action_movieListFragment_to_movieDetailsFragment)

            Navigation.createNavigateOnClickListener(
                R.id.action_movieListFragment_to_movieDetailsFragment, null
            )*/
        }
    }
}