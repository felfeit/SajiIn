package com.felfeit.sajiin.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felfeit.sajiin.R
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.databinding.ItemMealGridBinding

class HorizontalMealAdapter :
    ListAdapter<MealDetailModel, HorizontalMealAdapter.HorizontalMealViewHolder>(DIFF_CALLBACK) {

    var onClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalMealViewHolder {
        val binding =
            ItemMealGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HorizontalMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalMealViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)
    }

    inner class HorizontalMealViewHolder(val binding: ItemMealGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meal: MealDetailModel) {
            binding.apply {
                tvMealName.text = meal.name
                tvMealCategory.text = meal.category

                Glide.with(ivMealImage.context)
                    .load(meal.thumbnail)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(ivMealImage)

                root.setOnClickListener {
                    onClick?.invoke(meal.id)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MealDetailModel>() {
            override fun areItemsTheSame(
                oldItem: MealDetailModel,
                newItem: MealDetailModel,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MealDetailModel,
                newItem: MealDetailModel,
            ): Boolean = oldItem.name == newItem.name
        }
    }
}