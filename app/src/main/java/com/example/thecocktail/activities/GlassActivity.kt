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
import com.example.thecocktail.databinding.ActivityCategoryBinding
import com.example.thecocktail.databinding.ActivityGlassBinding
import com.example.thecocktail.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlassBinding
    private lateinit var adapter: CocktailAdapter
    private var cocktailList: List<Cocktail> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CocktailAdapter(cocktailList) { position ->
            navigateToDetail(cocktailList[position])
        }

        binding.recyclerViewGlass.adapter = adapter
        binding.recyclerViewGlass.layoutManager = GridLayoutManager(this, 2)

        val glass = intent.getStringExtra("GLASS") ?: "Shot" // Valor por defecto si no se pasa categoría

        filterByGlass(glass)
    }

    private fun navigateToDetail(cocktail: Cocktail) {
        Toast.makeText(this, cocktail.name, Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("COCKTAIL_ID", cocktail.id)
        intent.putExtra("COCKTAIL_NAME", cocktail.name)
        intent.putExtra("COCKTAIL_IMAGE", cocktail.imageURL)
        intent.putExtra("COCKTAIL_INGREDIENTS", cocktail)
        intent.putExtra("COCKTAIL_INSTRUCTION", cocktail.instruction)
        startActivity(intent)
    }

    private fun filterByGlass(query: String) {
        val service: CocktailAPICall = RetrofitProvider.getRetrofit()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = service.filterByGlass(query)
                runOnUiThread {
                    if (!result.drinks.isNullOrEmpty()) {
                        cocktailList = result.drinks
                        adapter.updateData(cocktailList)
                    } else {
                        cocktailList = emptyList()
                        Toast.makeText(
                            this@GlassActivity,
                            "No se encontraron cócteles",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@GlassActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

