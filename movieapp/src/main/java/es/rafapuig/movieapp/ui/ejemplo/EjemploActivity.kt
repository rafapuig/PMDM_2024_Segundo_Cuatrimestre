package es.rafapuig.movieapp.ui.ejemplo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import es.rafapuig.movieapp.R
import es.rafapuig.movieapp.databinding.ActivityEjemploBinding
import kotlinx.coroutines.launch

class EjemploActivity : AppCompatActivity() {

    val binding by lazy { ActivityEjemploBinding.inflate(layoutInflater) }
    val viewModel : EjemploViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnIncrementar.setOnClickListener {
            viewModel.incrementarContador()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.tvContador.text = uiState.contador.toString()
                    binding.progressBar2.isVisible = uiState.isCalculando
                }
            }
        }
    }

}