package com.example.thecocktail.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.thecocktail.adapters.CocktailAdapter
import com.example.thecocktail.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLinearLayout.visibility = View.VISIBLE

        simulateProgressAndNavigate()
    }

    private fun simulateProgressAndNavigate() {
        // Simulación de progreso
        CoroutineScope(Dispatchers.Main).launch {
            for (i in 0..100) {
                // Actualiza el progreso en el ProgressBar
                binding.progressBar.max = 100
                binding.progressBar.progress = i

                // Espera un momento para simular el avance de la tarea
                delay(10)
            }
            binding.mainLinearLayout.visibility = View.GONE // Oculta el LinearLayout de carga
            navigateToListActivity()
        }
    }

    private fun navigateToListActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
        finish()

    }
}


