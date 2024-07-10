package com.example.thecocktail.activities

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thecocktail.adapters.CocktailAdapter
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.data.CocktailAPICall
import com.example.thecocktail.databinding.ActivityListBinding
import com.example.thecocktail.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: CocktailAdapter
    private var cocktailList: List<Cocktail> = emptyList()

    private var categories: Array<String> = emptyArray()
    private var glasses: Array<String> = emptyArray()
    private var ingredients: Array<String> = emptyArray()
    private var alcoholicTypes: Array<String> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CocktailAdapter(cocktailList) { position ->
            navigateToDetail(cocktailList[position])
        }

        binding.recyclerViewList.adapter = adapter
        binding.recyclerViewList.layoutManager = GridLayoutManager(this, 2)

//        setupSpinners()
//        fetchFilterLists()
//        loadDefaultData()
    }

//    private fun loadDefaultData() {
//        // Cargar datos por defecto al iniciar la actividad (puedes usar el primer elemento de cada Spinner o algún valor predeterminado)
//        val defaultCategory = categories.firstOrNull() ?: "Ordinary Drink"
//        filterBySelectedFilters(defaultCategory, null, null, null)
//    }
//
//    private fun setupSpinners() {
//        // Crear adaptadores para los Spinners (Puedes usar datos estáticos o dinámicos según tus necesidades)
//        val categories = arrayOf("Ordinary Drink", "Cocktail", "Milk / Float / Shake")
//        val glasses = arrayOf("Highball glass", "Cocktail glass", "Old-fashioned glass")
//        val ingredients = arrayOf("Vodka", "Rum", "Gin")
//        val alcoholicTypes = arrayOf("Alcoholic", "Non alcoholic", "Optional alcohol")
//
//        binding.spinnerCategories.adapter =
//            ArrayAdapter(this, R.layout.simple_spinner_item, categories)
//        binding.spinnerGlasses.adapter =
//            ArrayAdapter(this, android.R.layout.simple_spinner_item, glasses)
//        binding.spinnerIngredients.adapter =
//            ArrayAdapter(this, android.R.layout.simple_spinner_item, ingredients)
//        binding.spinnerAlcoholicTypes.adapter =
//            ArrayAdapter(this, android.R.layout.simple_spinner_item, alcoholicTypes)
//
//        // Manejar selecciones de Spinners (implementar la lógica de filtrado en cada caso)
//        binding.spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedCategory = categories[position]
//                filterBySelectedFilters(selectedCategory, null, null, null) // Filtrar solo por categoría
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // No se hace nada
//            }
//        }
//
//        binding.spinnerGlasses.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedGlass = glasses[position]
//                filterBySelectedFilters(null, selectedGlass, null, null) // Filtrar solo por tipo de vaso
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // No se hace nada
//            }
//        }
//
//        binding.spinnerIngredients.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedIngredient = ingredients[position]
//                filterBySelectedFilters(null, null, selectedIngredient, null) // Filtrar solo por ingrediente
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // No se hace nada
//            }
//        }
//
//        binding.spinnerAlcoholicTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedAlcoholicType = alcoholicTypes[position]
//                filterBySelectedFilters(null, null, null, selectedAlcoholicType) // Filtrar solo por tipo de alcohol
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // No se hace nada
//            }
//        }
//    }
//
//    private fun filterBySelectedFilters(
//        category: String?,
//        glass: String?,
//        ingredient: String?,
//        alcoholicType: String?
//    ) {
//        val service: CocktailAPICall = RetrofitProvider.getRetrofit()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                // Realizar la llamada a la API basada en el filtro seleccionado
//                val result = when {
//                    category != null -> service.filterByCategory(category)
//                    glass != null -> service.filterByGlass(glass)
//                    ingredient != null -> service.listAllIngredients(ingredient)
//                    alcoholicType != null -> service.filterByType(alcoholicType)
//                    else -> throw IllegalArgumentException("No se seleccionó ningún filtro válido")
//                }
//
//                // Actualizar la lista de cócteles en el hilo principal
//                runOnUiThread {
//                    if (!result.drinks.isNullOrEmpty()) {
//                        cocktailList = result.drinks
//                        adapter.updateData(cocktailList)
//                    } else {
//                        cocktailList = emptyList()
//                        Toast.makeText(
//                            this@ListActivity,
//                            "No se encontraron cócteles",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                runOnUiThread {
//                    Toast.makeText(
//                        this@ListActivity,
//                        "Error: ${e.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
//
//    private fun fetchFilterLists() {
//        val service: CocktailAPICall = RetrofitProvider.getRetrofit()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                // Realizar todas las llamadas de manera concurrente usando async
//                val categoryDeferred = async { service.listCategories().execute() }
//                val glassDeferred = async { service.listGlasses().execute() }
//                val ingredientDeferred = async { service.listIngredients().execute() }
//                val alcoholicDeferred = async { service.listAlcoholic().execute() }
//
//                // Obtener los resultados de las llamadas
//                val categoryResponse = categoryDeferred.await()
//                val glassResponse = glassDeferred.await()
//                val ingredientResponse = ingredientDeferred.await()
//                val alcoholicResponse = alcoholicDeferred.await()
//
//                // Procesar las respuestas en el hilo principal
//                runOnUiThread {
//                    if (categoryResponse.isSuccessful) {
//                        val categoryList = categoryResponse.body()?.drinks?.map { it.strCategory }?.toTypedArray()
//                            ?: emptyArray()
//                        binding.spinnerCategories.adapter = ArrayAdapter(
//                            this@ListActivity,
//                            android.R.layout.simple_spinner_item,
//                            categoryList
//                        )
//                    } else {
//                        Toast.makeText(
//                            this@ListActivity,
//                            "Error fetching categories: ${categoryResponse.message()}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    if (glassResponse.isSuccessful) {
//                        val glassList = glassResponse.body()?.drinks?.map { it.strGlass }?.toTypedArray()
//                            ?: emptyArray()
//                        binding.spinnerGlasses.adapter = ArrayAdapter(
//                            this@ListActivity,
//                            android.R.layout.simple_spinner_item,
//                            glassList
//                        )
//                    } else {
//                        Toast.makeText(
//                            this@ListActivity,
//                            "Error fetching glasses: ${glassResponse.message()}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    if (ingredientResponse.isSuccessful) {
//                        val ingredientList = ingredientResponse.body()?.drinks?.map { it.strIngredient1 }?.toTypedArray()
//                            ?: emptyArray()
//                        binding.spinnerIngredients.adapter = ArrayAdapter(
//                            this@ListActivity,
//                            android.R.layout.simple_spinner_item,
//                            ingredientList
//                        )
//                    } else {
//                        Toast.makeText(
//                            this@ListActivity,
//                            "Error fetching ingredients: ${ingredientResponse.message()}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    if (alcoholicResponse.isSuccessful) {
//                        val alcoholicList = alcoholicResponse.body()?.drinks?.map { it.strAlcoholic }?.toTypedArray()
//                            ?: emptyArray()
//                        binding.spinnerAlcoholicTypes.adapter = ArrayAdapter(
//                            this@ListActivity,
//                            android.R.layout.simple_spinner_item,
//                            alcoholicList
//                        )
//                    } else {
//                        Toast.makeText(
//                            this@ListActivity,
//                            "Error fetching alcoholic types: ${alcoholicResponse.message()}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                runOnUiThread {
//                    Toast.makeText(
//                        this@ListActivity,
//                        "Error fetching filter lists: ${e.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }


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
}
    /*private fun filterByCategory(query: String) {
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
                            this@ListActivity,
                            "No se encontraron cócteles",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@ListActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    /*private fun navigateToDetail(cocktail: Cocktail) {
        // Aquí puedes navegar a la DetailActivity o realizar cualquier otra acción al seleccionar un cóctel
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("COCKTAIL_ID", cocktail.id)
            putExtra("COCKTAIL_NAME", cocktail.name)
            putExtra("COCKTAIL_IMAGE", cocktail.imageURL)
            putExtra("COCKTAIL_INGREDIENTS", cocktail)
            putExtra("COCKTAIL_INSTRUCTION", cocktail.instruction)
        }
        startActivity(intent)
    }*/
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
}*/