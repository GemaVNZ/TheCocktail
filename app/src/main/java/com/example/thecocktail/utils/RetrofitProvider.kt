package com.example.thecocktail.utils

import com.example.thecocktail.data.CocktailAPICall
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    companion object {
        fun getRetrofit(): CocktailAPICall {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CocktailAPICall::class.java)
        }
    }
}