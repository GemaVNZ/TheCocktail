package com.example.thecocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
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
    }
    //Funciones para declarar los datos que vamos a utilizar y con que vista.
    private lateinit var cocktail: Cocktail
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val id = intent.getIntExtra("COCKTAIL_ID", -1)
        cocktail = intent.getParcelableExtra("COCKTAIL_INGREDIENTS")!!
        val name = intent.getStringExtra("COCKTAIL_NAME")
        val image = intent.getStringExtra("COCKTAIL_IMAGE")

        val instruction = intent.getStringExtra("COCKTAIL_INSTRUCTION")
        if(instruction !=null) {
            Log.d("DetailActivity", "Instruction received from intent: $instruction")
        } else {
            Log.d("DetailActivity", "Instruction is null")
        }

        //Log.d("DetailActivity", "Received instruction: $instruction")
        //Log.d("DetailActivity", "Cocktail instruction: ${cocktail.instruction}")

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = cocktail.name
            setIcon(R.drawable.ic_share)
        }

        //initActionBar()

    Picasso.get().load(image).into(binding.imageViewCocktail)

    loadData(image,instruction)
}

//    private fun initActionBar () {
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = cocktail.name
//        supportActionBar?.setIcon(R.drawable.ic_share)
//    }

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