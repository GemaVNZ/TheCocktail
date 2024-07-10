package com.example.thecocktail.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.thecocktail.data.Cocktail.CategoryListResponse
import com.example.thecocktail.data.Cocktail.GlassListResponse
import com.example.thecocktail.data.Cocktail.IngredientListResponse
import com.example.thecocktail.data.Cocktail.AlcoholicListResponse

interface CocktailAPICall {
    //Función para buscar por el nombre del cocktail
    @GET("search.php")
    suspend fun findCocktailByName(@Query("s") query: String): CocktailResponse

    //Función para buscar el cocktail por el ingrediente
    @GET("search.php")
    suspend fun ingredientscocktailByName(@Query("i") query: String): Call<CocktailResponse>

    //Función para buscar un cocktail aleatorio
    @GET("random.php")
    suspend fun randomCocktail() : CocktailResponse

//    @GET ("filter.php")
//    suspend fun filterByType(@Query("a") query: String): CocktailResponse
//
//    @GET ("filter.php")
//    suspend fun filterByGlass(@Query("g") query: String): CocktailResponse
//
//    @GET ("filter.php")
//    suspend fun filterByCategory(@Query("c") query: String): CocktailResponse

    @GET ("filter.php")

    suspend fun listAllIngredients(@Query("i") query: String) : CocktailResponse

//    @GET("list.php?c=list")
//    fun listCategories(): Call<CategoryListResponse>
//
//    @GET("list.php?g=list")
//    fun listGlasses(): Call<GlassListResponse>
//
//    @GET("list.php?i=list")
//    fun listIngredients(): Call<IngredientListResponse>
//
//    @GET("list.php?a=list")
//    fun listAlcoholic(): Call<AlcoholicListResponse>


//    //Función para buscar por la primera letra
//    @GET("search.php")
//    suspend fun listCocktailByLetter(@Query("f") firstLetter: String): Call<CocktailResponse>

}