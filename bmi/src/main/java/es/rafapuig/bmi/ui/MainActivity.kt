package es.rafapuig.bmi.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.MutableCreationExtras
import es.rafapuig.bmi.BR
import es.rafapuig.bmi.BmiApplication
import es.rafapuig.bmi.R
import es.rafapuig.bmi.data.BmiState
import es.rafapuig.bmi.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val creationExtras = {
        MutableCreationExtras(defaultViewModelCreationExtras).apply {
            set(
                BmiViewModel.REPOSITORY_KEY,
                (application as BmiApplication).appContainer.repository
            )
        }
    }

    val creationExtrasFull = {
        MutableCreationExtras().apply {
            set(
                BmiViewModel.REPOSITORY_KEY,
                (application as BmiApplication).appContainer.repository
            )

            set(
                ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY,
                application
            )

            set(SAVED_STATE_REGISTRY_OWNER_KEY, this@MainActivity)

            set(VIEW_MODEL_STORE_OWNER_KEY, this@MainActivity)
        }
    }


    //private val viewModel: BmiViewModel by viewModels(creationExtras) { BmiViewModel.Factory }
    private val viewModel: BmiViewModel by viewModels { BmiViewModel.Factory }

    //private lateinit var viewModel: BmiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        //viewModel = ViewModelProvider.create(this, BmiViewModel.Factory, creationExtras())[BmiViewModel::class.java]

        binding.lifecycleOwner = this
        //binding.setVariable(BR.mainViewModel, viewModel)
        binding.mainViewModel = viewModel


        setContentView(binding.main)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
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
        //viewModel.bmi.observe(this) { updateUI() }

        viewModel.computingBMI.observe(this) { isComputing ->
            with(binding) {
                //progressBar.isVisible = isComputing
                resultadoNumber.visibility = if (isComputing) View.GONE else View.VISIBLE
                resultadoText.isVisible = !isComputing
            }
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
            //resultadoNumber.text = String.format(Locale.getDefault(), "%.2f", viewModel.bmi.value)
            //resultadoText.text = getString(getBmiState(viewModel.getResult()))
        }
    }

    private fun getBmiState(state: BmiState): Int {
        return when (state) {
            BmiState.UNDERWEIGHT -> R.string.underweight
            BmiState.NORMAL -> R.string.normal
            BmiState.OVERWEIGHT -> R.string.overweight
            BmiState.OBESITY -> R.string.obesity
        }
    }
}