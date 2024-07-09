package com.example.thecocktail.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.thecocktail.R
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import java.io.Serializable

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "COCKTAIL_ID"
        const val EXTRA_NAME = "COCKTAIL_NAME"
        const val EXTRA_IMAGE = "COCKTAIL_IMAGE"
        const val COCKTAIL = "COCKTAIL"
    }

    private lateinit var cocktail: Cocktail
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("COCKTAIL_ID", -1)
        val name = intent.getStringExtra("COCKTAIL_NAME")
        val image = intent.getStringExtra("COCKTAIL_IMAGE")

        cocktail = intent.getParcelableExtra("COCKTAIL")!!

        if (cocktail == null) {
            Toast.makeText(this, "Error: No se pudo obtener el cóctel", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.apply {
            //textViewName.text = cocktail.name
                cocktailCategoryTextView.text = cocktail.category
                cocktailAlcoholicTextView.text = cocktail.type
                cocktailGlassTextView.text = cocktail.glass
                cocktailInstructions.text = cocktail.instruction

            Picasso.get().load(cocktail.imageURL).into(imageViewCocktail)

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

    initActionBar(name)

    Picasso.get().load(image).into(binding.imageViewCocktail)

    loadData()
}

    private fun initActionBar (name: String?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = name
    }
    private fun loadData() {
        try {
        binding.cocktailCategoryTextView.text = cocktail.category
        binding.cocktailAlcoholicTextView.text = cocktail.type
        binding.cocktailGlassTextView.text = cocktail.glass
        binding.cocktailInstructions.text = cocktail.instruction



        /*var ingredientsText = ""
        cocktail.ingredients.forEachIndexed { index, ingredient ->
            if (index > 0) ingredientsText += "\n"
            ingredientsText += " - ${ingredient.name} (${ingredient.measure})"
        }
            binding.cocktailIngredients.text = ingredientsText*/

        } catch (e: NullPointerException) {
            Toast.makeText(this, "Error loading cocktail data", Toast.LENGTH_SHORT).show()
            finish()
        }
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