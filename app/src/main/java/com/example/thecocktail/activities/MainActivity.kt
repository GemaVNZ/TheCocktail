package com.example.thecocktail.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecocktail.R
import com.example.thecocktail.adapters.CocktailAdapter
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.data.CocktailAPICall
import com.example.thecocktail.databinding.ActivityMainBinding
import com.example.thecocktail.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CocktailAdapter
    private var cocktailList: List<Cocktail> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLinearLayout.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            navigateToListActivity()
        }
    }



    private fun navigateToListActivity() {
        //Toast.makeText(this, cocktail.name, Toast.LENGTH_LONG).show()
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)

    }

//Función para buscar por el nombre del cóctel
    private fun findCocktailByName(query: String) {
        val service: CocktailAPICall = RetrofitProvider.getRetrofit()
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = service.findCocktailByName(query)
        runOnUiThread {
            if (!result.drinks.isNullOrEmpty()) {
               cocktailList = result.drinks
               adapter.updateData(cocktailList)
            } else {
               cocktailList = emptyList()
               Toast.makeText(
                   this@MainActivity,
                   "No se encontraron cócteles",
                   Toast.LENGTH_SHORT
               ).show()
           }
       }
            } catch (e: Exception) {
            e.printStackTrace()
       runOnUiThread {
           // Puedes mostrar un mensaje de error genérico al usuario si ocurre una excepción
           Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
               .show()
       }
   }
}
}
}


