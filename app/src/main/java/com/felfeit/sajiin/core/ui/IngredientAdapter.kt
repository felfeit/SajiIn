package com.felfeit.sajiin.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felfeit.sajiin.core.domain.models.Ingredient
import com.felfeit.sajiin.databinding.ItemIngredientBinding

class IngredientAdapter :
    ListAdapter<Ingredient, IngredientAdapter.IngredientViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)
    }

    inner class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient) {
            binding.apply {
                tvIngredientName.text = ingredient.name
                tvIngredientMeasure.text = ingredient.measure
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(
                oldItem: Ingredient,
                newItem: Ingredient,
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: Ingredient,
                newItem: Ingredient,
            ): Boolean = oldItem == newItem
        }
    }
}