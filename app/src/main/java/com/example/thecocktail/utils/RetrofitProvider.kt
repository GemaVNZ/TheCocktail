package com.example.thecocktail.utils

import com.example.thecocktail.data.CocktailAPICall
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit() : CocktailAPICall {
            val retrofit = Retrofit.Builder()
                .baseUrl("www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CocktailAPICall::class.java)
        }
    }
}