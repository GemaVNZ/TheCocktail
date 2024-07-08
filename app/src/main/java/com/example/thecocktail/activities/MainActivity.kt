package com.example.thecocktail.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecocktail.DetailActivity
import com.example.thecocktail.R
import com.example.thecocktail.adapters.CocktailAdapter
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CocktailAdapter
    private var cocktailList: List<Cocktail> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CocktailAdapter(cocktailList){ position ->
            navigateToDetail(cocktailList[position])
        }

        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = GridLayoutManager(this, 2)

    }


    private fun navigateToDetail(cocktail: Cocktail) {
        //Toast.makeText(this, superhero.name, Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("COCKTAIL_ID", cocktail.id)
        intent.putExtra("COCKTAIL_NAME", cocktail.name)
        intent.putExtra("SUPERHERO_IMAGE", cocktail.imageURL)
        startActivity(intent)

    }

}