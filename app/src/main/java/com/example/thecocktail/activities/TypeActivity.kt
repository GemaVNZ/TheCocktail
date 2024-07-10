package com.example.thecocktail.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecocktail.R
import com.example.thecocktail.adapters.CocktailAdapter
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.data.CocktailAPICall
import com.example.thecocktail.databinding.ActivityGlassBinding
import com.example.thecocktail.databinding.ActivityTypeBinding
import com.example.thecocktail.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTypeBinding
    private lateinit var adapter: CocktailAdapter
    private var cocktailList: List<Cocktail> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CocktailAdapter(cocktailList) { position ->
            navigateToDetail(cocktailList[position])
        }

        binding.recyclerViewType.adapter = adapter
        binding.recyclerViewType.layoutManager = GridLayoutManager(this, 2)

        val type = intent.getStringExtra("Type") ?: "Alcoholic" // Valor por defecto si no se pasa categoría

        filterByType(type)
    }

    private fun filterByType(query: String) {
        val service: CocktailAPICall = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = service.filterByCategory(query)
                runOnUiThread {
                    if (!result.drinks.isNullOrEmpty()) {
                        cocktailList = result.drinks
                        adapter.updateData(cocktailList)
                    } else {
                        cocktailList = emptyList()
                        Toast.makeText(
                            this@TypeActivity,
                            "No se encontraron cócteles",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@TypeActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun navigateToDetail(cocktail: Cocktail) {
        // Aquí puedes navegar a la DetailActivity o realizar cualquier otra acción al seleccionar un cóctel
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("COCKTAIL_ID", cocktail.id)
            putExtra("COCKTAIL_NAME", cocktail.name)
            putExtra("COCKTAIL_IMAGE", cocktail.imageURL)
            putExtra("COCKTAIL_INGREDIENTS", cocktail)
            putExtra("COCKTAIL_INSTRUCTION", cocktail.instruction)
        }
        startActivity(intent)
    }
}