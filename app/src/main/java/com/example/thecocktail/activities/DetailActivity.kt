package com.example.thecocktail.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.thecocktail.R
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    //Definimos los diferentes objetos de la clase.
    companion object {
        const val EXTRA_ID = "COCKTAIL_ID"
        const val EXTRA_NAME = "COCKTAIL_NAME"
        const val EXTRA_IMAGE = "COCKTAIL_IMAGE"
        const val EXTRA_COCKTAIL = "COCKTAIL_INGREDIENTS"
        const val EXTRA_INSTRUCTION = "COCKTAIL_INSTRUCTION"
        const val PREFS_NAME = "favorites"
        const val PREFIX_FAVORITE = "favorite_"

    }
    //Funciones para declarar los datos que vamos a utilizar y con que vista.
    private lateinit var cocktail: Cocktail
    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("COCKTAIL_ID", -1)
        cocktail = intent.getParcelableExtra("COCKTAIL_INGREDIENTS")!!
        val name = intent.getStringExtra("COCKTAIL_NAME")
        val image = intent.getStringExtra("COCKTAIL_IMAGE")

        val instruction = intent.getStringExtra("COCKTAIL_INSTRUCTION")
        if(instruction !=null) {
            Log.d("DetailActivity", "Instruction received from intent: $instruction")
        } else {
            Log.d("DetailActivity", "Instruction is null")
        }


        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = cocktail.name
        }

    Picasso.get().load(image).into(binding.imageViewCocktail)

        loadData(image,instruction)

        isFavorite = isCocktailFavorite(cocktail.id.toString())

        updateFavoriteButtonState()

        binding.fabFavorite.setOnClickListener {
            changeFavoriteState()
        }


}

    // Función para actualizar visualmente el botón de favorito
    private fun changeFavoriteState() {
        isFavorite = !isFavorite

        if (isFavorite) {
            saveFavoriteCocktail(cocktail.id.toString())
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
        } else {
            removeFavoriteCocktail(cocktail.id.toString())
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
        }

        updateFavoriteButtonState()
    }

    private fun saveFavoriteCocktail(cocktailId: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(cocktailId, true)
        editor.apply()
    }

    private fun removeFavoriteCocktail(cocktailId: String) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Remove from Favorites")
            setMessage("Are you sure you want to remove this cocktail from favorites?")
            setPositiveButton("Yes") { dialog, which ->
                val editor = sharedPreferences.edit()
                editor.remove(cocktailId)
                editor.apply()
                Toast.makeText(this@DetailActivity, "Removed from favorites", Toast.LENGTH_SHORT).show()
                updateFavoriteButtonState()
            }
            setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            show()
        }
    }

    private fun isCocktailFavorite(cocktailId: String): Boolean {
        return sharedPreferences.contains(cocktailId)
    }

    private fun updateFavoriteButtonState() {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun loadData(image: String?, instruction: String?) {
        try {
            binding.apply {
                cocktailCategoryTextView.text = cocktail.category
                cocktailAlcoholicTextView.text = cocktail.type
                cocktailGlassTextView.text = cocktail.glass
                cocktailInstructions.text = instruction ?: cocktail.instruction

                //Cargar la imagen en el ImageView
                Picasso.get().load(cocktail.imageURL).into(imageViewCocktail)

                //Construir la lista de ingredientes y medidas para mostrar en el TextView
                var ingredientsText = ""
                for (i in 1..15) {
                    val ingredient = cocktail.getIngredient(i)
                    val measure = cocktail.getMeasure(i)
                    if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                        if (ingredientsText.isNotEmpty()) {
                            ingredientsText += "\n"
                        }
                        ingredientsText += "$ingredient - $measure"
                    }
                }
                cocktailIngredients.text = ingredientsText
            }

        } catch (e: NullPointerException) {
            Toast.makeText(this, "Error loading cocktail data", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    //app:actionViewClass="androidx.appcompat.widget.SearchView"

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }



}