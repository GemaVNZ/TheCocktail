package com.example.thecocktail.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.thecocktail.R

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "COCKTAIL_ID"
        const val EXTRA_NAME = "COCKTAIL_NAME"
        const val EXTRA_IMAGE = "COCKTAIL_IMAGE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayShowTitleEnabled(false)


    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}