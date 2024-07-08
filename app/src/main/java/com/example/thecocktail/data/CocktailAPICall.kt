package com.example.thecocktail.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailAPICall {
    @GET ("search.php")
    suspend fun findCocktailByName(@Query("s") query: String) : Call <CocktailResponse>

    @GET ("search.php")
    suspend fun listCocktailByLetter(@Query("f") firstLetter: String) : Call <CocktailResponse>

    @GET ("lookup.php")
    suspend fun cocktailDetailsById(@Query("i") id : Int) : Call <CocktailResponse>

}