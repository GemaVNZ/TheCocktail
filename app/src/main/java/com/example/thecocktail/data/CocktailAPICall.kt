package com.example.thecocktail.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CocktailAPICall {
    //Función para buscar por el nombre del cocktail
    @GET("search.php")
    suspend fun findCocktailByName(@Query("s") query: String): CocktailResponse

    //Función para buscar un cocktail aleatorio
    @GET("random.php")
    suspend fun randomCocktail() : CocktailResponse

}