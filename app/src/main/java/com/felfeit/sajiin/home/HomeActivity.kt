package com.felfeit.sajiin.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.felfeit.sajiin.R
import com.felfeit.sajiin.bookmark.BookmarkActivity
import com.felfeit.sajiin.core.data.Resource
import com.felfeit.sajiin.core.domain.models.MealCategory
import com.felfeit.sajiin.core.ui.HorizontalMealAdapter
import com.felfeit.sajiin.core.ui.MealCategoryAdapter
import com.felfeit.sajiin.core.ui.VerticalMealAdapter
import com.felfeit.sajiin.databinding.ActivityHomeBinding
import com.felfeit.sajiin.meals.MealActivity
import com.felfeit.sajiin.recipe.RecipeActivity
import com.felfeit.sajiin.search.SearchActivity
import dev.androidbroadcast.vbpd.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.jvm.java
import kotlin.random.Random

class HomeActivity : AppCompatActivity(R.layout.activity_home) {
    private val binding by viewBinding(ActivityHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()
    private val recommendedMealAdapter = HorizontalMealAdapter().apply {
        onClick = { mealId ->
            Intent(this@HomeActivity, RecipeActivity::class.java).apply {
                putExtra(RecipeActivity.RECIPE_EXTRA, mealId)
                startActivity(this)
            }
        }
    }
    private val popularMealAdapter = VerticalMealAdapter(false).apply {
        onClick = { mealId ->
            Intent(this@HomeActivity, RecipeActivity::class.java).apply {
                putExtra(RecipeActivity.RECIPE_EXTRA, mealId)
                startActivity(this)
            }
        }
    }
    private val mealCategoryAdapter = MealCategoryAdapter().apply {
        onClick = { category ->
            Intent(this@HomeActivity, MealActivity::class.java).apply {
                putExtra(MealActivity.MEAL_EXTRA, category.name)
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

        setSupportActionBar(binding.toolbarHome)
        setupRecyclerViews()
        loadCategories()
        observeData()
        setupButtons()
    }

    private fun setupButtons() {
        binding.contentHomeLayout.buttonBookmarks.setOnClickListener {
            startActivity(Intent(this@HomeActivity, BookmarkActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this@HomeActivity, SearchActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerViews() {
        binding.contentHomeLayout.apply {
            rvMealCategories.apply {
                layoutManager = GridLayoutManager(this@HomeActivity, 4)
                adapter = mealCategoryAdapter
            }

            rvRecommendedMeals.apply {
                layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = recommendedMealAdapter
            }

            rvPopularMeals.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity)
                adapter = popularMealAdapter
            }
        }
    }

    private fun loadCategories() {
        val categoriesArray = resources.getStringArray(R.array.categories)
        val categoriesList = categoriesArray.map { item ->
            val parts = item.split("|")
            MealCategory(parts[0], parts[1], parts[2])
        }
        mealCategoryAdapter.submitList(categoriesList)
    }

    private fun observeData() {
        val lettersArray = resources.getStringArray(R.array.letters)
        val randomLetters = lettersArray.toList().shuffled(Random).take(2)
        val recommendedLetter = randomLetters.getOrNull(0) ?: "a"
        val popularLetter = randomLetters.getOrNull(1) ?: "b"

        observeRecommendedMealRecipes(recommendedLetter)
        observePopularMealRecipes(popularLetter)
    }

    private fun observeRecommendedMealRecipes(letter: String) {
        viewModel.getRecommendedMealRecipes(letter).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showMessage(false)
                    showContent(false)
                }

                is Resource.Error -> {
                    showLoading(false)
                    showMessage(true, resource.message)
                    showContent(false)
                }

                is Resource.Success -> {
                    showMessage(false)
                    showLoading(false)
                    showContent(true)
                    recommendedMealAdapter.submitList(resource.data)
                }
            }
        }
    }

    private fun observePopularMealRecipes(letter: String) {
        viewModel.getPopularMealRecipes(letter).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Error -> Toast.makeText(this, resource.message, Toast.LENGTH_SHORT)
                    .show()

                is Resource.Success -> popularMealAdapter.submitList(resource.data)
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

    private fun showContent(show: Boolean) {
        binding.contentScrollView.visibility = if (show) View.VISIBLE else View.GONE
    }
}