package com.felfeit.sajiin.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.felfeit.sajiin.R
import com.felfeit.sajiin.core.data.Resource
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.ui.IngredientAdapter
import com.felfeit.sajiin.databinding.ActivityRecipeBinding
import dev.androidbroadcast.vbpd.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.net.toUri

class RecipeActivity : AppCompatActivity(R.layout.activity_recipe) {
    private val binding by viewBinding(ActivityRecipeBinding::bind)
    private val viewModel: RecipeViewModel by viewModel()
    private val adapter = IngredientAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mealId = intent.getStringExtra(RECIPE_EXTRA)
        if (mealId == null) {
            Toast.makeText(this, "Meal ID not found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupButtons()
        setupRecyclerViews()
        observeViewModel(mealId)
    }

    private fun setupButtons() {
        binding.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.buttonBookmark.visibility = View.GONE
    }

    private fun setupRecyclerViews() {
        binding.contentRecipeLayout.rvIngredients.apply {
            layoutManager = LinearLayoutManager(this@RecipeActivity)
            adapter = this@RecipeActivity.adapter
        }
    }

    private fun observeViewModel(mealId: String) {
        viewModel.getMealRecipe(mealId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showMessage(false)
                    showContent(false)
                    binding.buttonBookmark.visibility = View.GONE
                }

                is Resource.Success -> {
                    showLoading(false)
                    if (resource.data == null) {
                        showMessage(true, getString(R.string.meal_not_found))
                        showContent(false)
                        binding.buttonBookmark.visibility = View.GONE
                    } else {
                        showMessage(false)
                        showContent(true)
                        displayMealDetail(resource.data)
                        viewModel.checkFavoriteStatus(resource.data.id)

                        binding.buttonBookmark.visibility = View.VISIBLE
                        binding.buttonBookmark.setOnClickListener {
                            viewModel.toggleFavorite(resource.data)
                        }

                        binding.contentRecipeLayout.buttonYoutube.setOnClickListener {
                            val youtubeLink = resource.data.youtubeLink
                            if (youtubeLink.isNotBlank()) {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, youtubeLink.toUri())
                                    startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(this, "Could not open YouTube link.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this, "YouTube link not available.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    showMessage(
                        true,
                        resource.message ?: getString(R.string.error_fetching_meal_detail)
                    )
                    showContent(false)
                    binding.buttonBookmark.visibility = View.GONE

                }
            }
        }

        viewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.buttonBookmark.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_fill)
            } else {
                binding.buttonBookmark.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_outline)
            }
        }
    }

    private fun displayMealDetail(meal: MealDetailModel) {
        binding.contentRecipeLayout.apply {
            Glide.with(this@RecipeActivity)
                .load(meal.thumbnail)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(ivMealThumbnail)

            tvMealName.text = meal.name
            tvMealCategory.text = meal.category
            tvMealArea.text = meal.area
            tvInstruction.text = meal.instructions
            tvIngredientsCount.text = "${meal.ingredients.size} Ingredients"
            adapter.submitList(meal.ingredients)

            buttonYoutube.visibility = if (meal.youtubeLink.isBlank()) View.GONE else View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.circleLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(show: Boolean, message: String? = null) {
        binding.tvMessage.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvMessage.text = message
    }

    private fun showContent(show: Boolean) {
        binding.contentScrollView.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        const val RECIPE_EXTRA = "recipe_extra"
    }
}