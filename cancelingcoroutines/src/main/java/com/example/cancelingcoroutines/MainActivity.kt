package com.example.cancelingcoroutines

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.cancelingcoroutines.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    var count = 100

    lateinit var binding: ActivityMainBinding

    var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        job = lifecycleScope.launch {
            try {
                while (count > 0) {
                    delay(100)
                    countdown()
                }
            }catch (exception:Exception) {
                Snackbar.make(binding.textView, exception.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }

        binding.button.setOnClickListener {
            job?.cancel()
        }


    }

    fun countdown() {
        count--
        binding.textView.text = count.toString()
        if((0..9).random() == 0) throw Exception("Se produjo un error")
    }

}