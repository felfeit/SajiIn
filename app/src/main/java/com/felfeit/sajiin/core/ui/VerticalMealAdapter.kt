package com.felfeit.sajiin.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.felfeit.sajiin.R
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.models.MealModel
import com.felfeit.sajiin.databinding.ItemMealListBinding

class VerticalMealAdapter(private val isBookmark: Boolean) :
    ListAdapter<Any, VerticalMealAdapter.VerticalMealViewHolder>(DIFF_CALLBACK) {

    var onClick: ((String) -> Unit)? = null
    var onBookmarkClick: ((MealDetailModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalMealViewHolder {
        val binding =
            ItemMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalMealViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, isBookmark, onBookmarkClick)
    }

    inner class VerticalMealViewHolder(val binding: ItemMealListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Any,
            isBookmark: Boolean,
            onBookmarkClick: ((MealDetailModel) -> Unit)?
        ) {
            binding.apply {
                val mealId: String
                val mealName: String
                val mealCategory: String?
                val mealArea: String?
                val mealImageUrl: String?

                when (item) {
                    is MealDetailModel -> {
                        mealId = item.id
                        mealName = item.name
                        mealCategory = item.category
                        mealArea = item.area
                        mealImageUrl = item.thumbnail
                    }
                    is MealModel -> {
                        mealId = item.id
                        mealName = item.name
                        mealCategory = item.category
                        mealArea = item.area
                        mealImageUrl = item.image
                    }
                    else -> {
                        tvMealName.text = "Tipe Makanan Tidak Dikenal"
                        tvMealCategory.text = ""
                        tvMealDetails.text = ""
                        ivMealImage.setImageResource(R.drawable.placeholder)
                        bookmarkButton.visibility = View.GONE
                        root.setOnClickListener(null)
                        return
                    }
                }

                // Set teks untuk TextViews
                tvMealName.text = mealName
                tvMealCategory.text = mealCategory
                tvMealDetails.text = mealArea

                // Load gambar
                Glide.with(ivMealImage.context)
                    .load(mealImageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(ivMealImage)


                if (isBookmark) {
                    bookmarkButton.visibility = View.VISIBLE
                    bookmarkButton.setOnClickListener {
                        onBookmarkClick?.invoke(item as MealDetailModel)
                    }
                } else {
                    bookmarkButton.visibility = View.GONE
                }

                root.setOnClickListener {
                    onClick?.invoke(mealId)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(
                oldItem: Any,
                newItem: Any,
            ): Boolean {
                if (oldItem::class != newItem::class) return false

                return when (oldItem) {
                    is MealDetailModel -> (newItem as MealDetailModel).id == oldItem.id
                    is MealModel -> (newItem as MealModel).id == oldItem.id
                    else -> oldItem == newItem
                }
            }

            override fun areContentsTheSame(
                oldItem: Any,
                newItem: Any,
            ): Boolean {
                if (oldItem::class != newItem::class) return false

                return when (oldItem) {
                    is MealDetailModel -> (newItem as MealDetailModel) == oldItem
                    is MealModel -> (newItem as MealModel) == oldItem
                    else -> false
                }
            }
        }
    }
}