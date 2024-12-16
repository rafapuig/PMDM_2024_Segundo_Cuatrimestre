package es.rafapuig.bmi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import es.rafapuig.bmi.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: BmiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initListeners()
        initObservers()
    }



    private fun initListeners() {
        binding.computeButton.setOnClickListener { onComputeBmi() }
    }

    private fun initObservers() {
        viewModel.bmi.observe(this) { updateUI() }
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

    fun getBmiState(state: BmiViewModel.BmiState): Int {
        return when (state) {
            BmiViewModel.BmiState.UNDERWEIGHT -> R.string.underweight
            BmiViewModel.BmiState.NORMAL -> R.string.normal
            BmiViewModel.BmiState.OVERWEIGHT -> R.string.overweight
            BmiViewModel.BmiState.OBESITY -> R.string.obesity
        }
    }
}