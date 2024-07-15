package com.example.thecocktail.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CocktailAdapter
    private var cocktailList: List<Cocktail> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToListActivity()

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


/* Menú para crear la búsqueda
override fun onCreateOptionsMenu(menu: Menu): Boolean {
menuInflater.inflate(R.menu.menu_main, menu)
val searchItem = menu.findItem(R.id.menu_search)
initSearchView(searchItem)
return true
}

//Añado el menú con el item de compartir
override fun onOptionsItemSelected(item: MenuItem): Boolean {
return when (item.itemId) {
   R.id.menu_share -> {
       shareCocktail()
       true
   }

   else -> super.onOptionsItemSelected(item)
}
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
}*/

//Función para mostrar en el detalle todos los datos del cocktail


/*Función para buscar por el nombre del cóctel
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

Función para que aparezca 1 cócktail random en el inicio
private fun randomCocktail() {
val service: CocktailAPICall = RetrofitProvider.getRetrofit()
//Llamada al segundo hilo
CoroutineScope(Dispatchers.IO).launch {
   try {
       val result = service.randomCocktail()
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

//Función para compartir
private fun shareCocktail() {
if (cocktailList.isNotEmpty()) {
   val currentCocktail =
       cocktailList[0] // Obtener el primer cóctel de la lista, o el cóctel seleccionado
   val shareIntent = Intent().apply {
       action = Intent.ACTION_SEND
       putExtra(Intent.EXTRA_SUBJECT, "Check out this cocktail!")
       putExtra(Intent.EXTRA_TEXT, "Check out this cocktail: ${currentCocktail.name}")
       type = "text/plain"
   }
   startActivity(Intent.createChooser(shareIntent, "Share via"))
} else {
   Toast.makeText(this, "No cocktail to share", Toast.LENGTH_SHORT).show()
}
}

}*/

/*        intent.putExtra("COCKTAIL_ID", cocktail.id)
intent.putExtra("COCKTAIL_NAME", cocktail.name)
intent.putExtra("COCKTAIL_IMAGE", cocktail.imageURL)
intent.putExtra("COCKTAIL_INGREDIENTS", cocktail)
intent.putExtra("COCKTAIL_INSTRUCTION", cocktail.instruction)

*/
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
                   this@MainActivity,
                   "No se encontraron cócteles",
                   Toast.LENGTH_SHORT
               ).show()
           }
       }
   } catch (e: Exception) {
       e.printStackTrace()
       runOnUiThread {
           Toast.makeText(
               this@MainActivity,
               "Error: ${e.message}",
               Toast.LENGTH_SHORT
           ).show()
       }
   }
}
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
                   this@MainActivity,
                   "No se encontraron cócteles",
                   Toast.LENGTH_SHORT
               ).show()
           }
       }
   } catch (e: Exception) {
       e.printStackTrace()
       runOnUiThread {
           Toast.makeText(
               this@MainActivity,
               "Error: ${e.message}",
               Toast.LENGTH_SHORT
           ).show()
       }
   }
}
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
                   this@MainActivity,
                   "No se encontraron cócteles",
                   Toast.LENGTH_SHORT
               ).show()
           }
       }
   } catch (e: Exception) {
       e.printStackTrace()
       runOnUiThread {
           Toast.makeText(
               this@MainActivity,
               "Error: ${e.message}",
               Toast.LENGTH_SHORT
           ).show()
       }
   }
}
}*/



