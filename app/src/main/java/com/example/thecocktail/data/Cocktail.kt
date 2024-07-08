package com.example.thecocktail.data

import android.media.Image
import com.google.gson.annotations.SerializedName


data class CocktailResponse(
    @SerializedName("drinks") val drinks: List<Cocktail>?
)
data class Cocktail (

    @SerializedName("idDrink") val id:Int,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strAlcoholic") val type : String,
    @SerializedName("strGlass") val glass : String,
    @SerializedName("strDrinkThumb") val imageURL : String,
    @SerializedName("strInstructions") val instruction: String,
    val ingredients: List<Ingredient>) {

    }

data class Ingredient (
    val name: String?,
    val measure: String?) {

}


