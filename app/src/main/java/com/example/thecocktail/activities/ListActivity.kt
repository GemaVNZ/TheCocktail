package com.example.thecocktail.activities

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecocktail.adapters.CocktailAdapter
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.data.CocktailAPICall
import com.example.thecocktail.databinding.ActivityListBinding
import com.example.thecocktail.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: CocktailAdapter
    private var cocktailList: List<Cocktail> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CocktailAdapter(cocktailList) { position ->
            navigateToDetail(cocktailList[position])
        }

        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = GridLayoutManager(this, 2)

        randomCocktails()
    }

    // Menú para crear la búsqueda
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.thecocktail.R.menu.menu_main, menu)
        val searchItem = menu.findItem(com.example.thecocktail.R.id.menu_search)
        initSearchView(searchItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }

            return super.onOptionsItemSelected(item)
        }


        //Menú para hacer la búsqueda
        private fun initSearchView(searchItem: MenuItem?) {
            if (searchItem != null) {
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            findCocktailByName(it)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }
        }

        //Función para mostrar en el detalle todos los datos del cocktail
        private fun navigateToDetail(cocktail: Cocktail) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("COCKTAIL_ID", cocktail.id)
            intent.putExtra("COCKTAIL_NAME", cocktail.name)
            intent.putExtra("COCKTAIL_IMAGE", cocktail.imageURL)
            intent.putExtra("COCKTAIL_INGREDIENTS", cocktail)
            intent.putExtra("COCKTAIL_INSTRUCTION", cocktail.instruction)
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
                                this@ListActivity,
                                "No se encontraron cócteles",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        // Puedes mostrar un mensaje de error genérico al usuario si ocurre una excepción
                        Toast.makeText(this@ListActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        //Función para que aparezca 1 cócktail random en el inicio
        private fun randomCocktails() {
            val service: CocktailAPICall = RetrofitProvider.getRetrofit()
            val numberOfCocktails = 10 // Número de cócteles aleatorios que deseas obtener
            val randomCocktailList = mutableListOf<Cocktail>()

            //Llamada al segundo hilo
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    repeat(numberOfCocktails) {
                        val result = service.randomCocktail()
                        if (!result.drinks.isNullOrEmpty()) {
                            randomCocktailList.addAll(result.drinks)
                        }
                    }

                    runOnUiThread {
                        if (randomCocktailList.isNotEmpty()) {
                            cocktailList = randomCocktailList
                            adapter.updateData(cocktailList)
                        } else {
                            cocktailList = emptyList()
                            Toast.makeText(
                                this@ListActivity,
                                "No se encontraron cócteles",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        // Puedes mostrar un mensaje de error genérico al usuario si ocurre una excepción
                        Toast.makeText(this@ListActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
