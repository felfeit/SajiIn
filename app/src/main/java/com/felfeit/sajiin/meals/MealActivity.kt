package com.felfeit.sajiin.meals

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.felfeit.sajiin.R
import com.felfeit.sajiin.core.data.Resource
import com.felfeit.sajiin.core.ui.VerticalMealAdapter
import com.felfeit.sajiin.databinding.ActivityMealBinding
import com.felfeit.sajiin.recipe.RecipeActivity
import dev.androidbroadcast.vbpd.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealActivity : AppCompatActivity(R.layout.activity_meal) {
    private val binding by viewBinding(ActivityMealBinding::bind)
    private val viewModel: MealViewModel by viewModel()
    private val adapter = VerticalMealAdapter(false).apply {
        onClick = { mealId ->
            Intent(this@MealActivity, RecipeActivity::class.java).apply {
                putExtra(RecipeActivity.RECIPE_EXTRA, mealId)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val category = intent.getStringExtra(MEAL_EXTRA)
        if (category == null) {
            Toast.makeText(this, "Category not found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar(category)
        setupRecyclerView()
        observeMealRecipes(category)
    }

    private fun setupToolbar(categoryName: String) {
        setSupportActionBar(binding.toolbarMeal)
        supportActionBar?.title = categoryName
        binding.toolbarMeal.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.rvMeals.apply {
            layoutManager = LinearLayoutManager(this@MealActivity)
            adapter = this@MealActivity.adapter
        }
    }

    private fun observeMealRecipes(category: String) {
        viewModel.getMealRecipesByCategory(category).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showMessage(false)
                    binding.rvMeals.visibility = View.GONE // Sembunyikan RecyclerView saat loading
                }

                is Resource.Success -> {
                    showLoading(false)
                    if (resource.data.isNullOrEmpty()) {
                        showMessage(
                            true,
                            getString(R.string.no_meals_in_category, category)
                        ) // Pesan jika tidak ada hasil
                        binding.rvMeals.visibility = View.GONE
                    } else {
                        showMessage(false)
                        binding.rvMeals.visibility = View.VISIBLE
                        adapter.submitList(resource.data)
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    showMessage(
                        true,
                        resource.message ?: getString(R.string.error_fetching_meals)
                    ) // Pesan error
                    binding.rvMeals.visibility = View.GONE
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.circleLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(show: Boolean, message: String? = null) {
        binding.tvMessage.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvMessage.text = message
    }

    companion object {
        const val MEAL_EXTRA = "meal_extra"
    }
}