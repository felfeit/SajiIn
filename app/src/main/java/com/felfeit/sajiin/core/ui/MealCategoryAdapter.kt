package com.felfeit.sajiin.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felfeit.sajiin.R
import com.felfeit.sajiin.core.domain.models.MealCategory
import com.felfeit.sajiin.databinding.ItemMealCategoryBinding

class MealCategoryAdapter :
    ListAdapter<MealCategory, MealCategoryAdapter.MealCategoryViewHolder>(DIFF_CALLBACK) {

    var onClick: ((MealCategory) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCategoryViewHolder {
        val binding =
            ItemMealCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealCategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    inner class MealCategoryViewHolder(val binding: ItemMealCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: MealCategory) {
            binding.apply {
                Glide.with(ivCategoryImage.context)
                    .load(category.image)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(ivCategoryImage)

                this.tvCategoryName.text = category.name

                root.setOnClickListener {
                    onClick?.invoke(category)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MealCategory>() {
            override fun areItemsTheSame(
                oldItem: MealCategory,
                newItem: MealCategory,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MealCategory,
                newItem: MealCategory,
            ): Boolean = oldItem == newItem
        }
    }
}