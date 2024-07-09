package com.example.thecocktail.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

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

//    //Función para buscar por la primera letra
//    @GET("search.php")
//    suspend fun listCocktailByLetter(@Query("f") firstLetter: String): Call<CocktailResponse>

}