package com.example.thecocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecocktail.data.Cocktail
import com.example.thecocktail.databinding.ItemCocktailBinding
import com.squareup.picasso.Picasso

class CocktailAdapter (private var dataSet : List <Cocktail> = emptyList(),
                       private val onItemClickListener:(Int) -> Unit ):
    RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(holder.adapterPosition)
        }
    }

    fun updateData(dataSet: List<Cocktail>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


    class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Método para pintar la vista
        fun render(cocktail: Cocktail) {
            binding.nameTextView.text = cocktail.name
            Picasso.get().load(cocktail.imageURL).into(binding.avatarImageView)
        }

    }
}