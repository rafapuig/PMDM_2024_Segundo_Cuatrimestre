package es.rafapuig.bmi.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.rafapuig.bmi.BR
import es.rafapuig.bmi.R
import es.rafapuig.bmi.data.BmiState
import es.rafapuig.bmi.data.RepositoryImpl
import es.rafapuig.bmi.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: BmiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.setVariable(BR.mainViewModel, viewModel)
        binding.lifecycleOwner = this

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Esta linea CAGA todo la arquitectura
        viewModel.repository = RepositoryImpl()

        initListeners()
        initObservers()
    }



    private fun initListeners() {
        binding.computeButton.setOnClickListener { onComputeBmi() }
    }

    private fun initObservers() {
        viewModel.bmi.observe(this) { updateUI() }

        viewModel.computingBMI.observe(this) { isComputing ->
            binding.progressBar.visibility = if (isComputing) View.VISIBLE else View.GONE
            binding.resultadoNumber.visibility = if(isComputing) View.GONE else View.VISIBLE
            binding.resultadoText.visibility = if(isComputing) View.GONE else View.VISIBLE
        }
    }


    private fun onComputeBmi() {

        viewModel.height = binding.estaturaEdit.text.toString().toDoubleOrNull() ?: 0.0
        viewModel.weight = binding.pesoEdit.text.toString().toDoubleOrNull() ?: 0.0

        viewModel.computeBMI()

        //binding.resultadoNumber.text = String.format(Locale.getDefault(), "%.2f", viewModel.bmi)
        //binding.resultadoText.text = getString(viewModel.getResult())
    }

    private fun updateUI() {
        with(binding) {
            resultadoNumber.text = String.format(Locale.getDefault(), "%.2f", viewModel.bmi.value)
            resultadoText.text = getString(getBmiState(viewModel.getResult()))
        }
    }

    fun getBmiState(state: BmiState): Int {
        return when (state) {
            BmiState.UNDERWEIGHT -> R.string.underweight
            BmiState.NORMAL -> R.string.normal
            BmiState.OVERWEIGHT -> R.string.overweight
            BmiState.OBESITY -> R.string.obesity
        }
    }
}