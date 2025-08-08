package com.felfeit.sajiin.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.felfeit.sajiin.R
import com.felfeit.sajiin.core.ui.VerticalMealAdapter
import com.felfeit.sajiin.databinding.ActivityBookmarkBinding
import com.felfeit.sajiin.recipe.RecipeActivity
import com.google.android.material.snackbar.Snackbar
import dev.androidbroadcast.vbpd.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkActivity : AppCompatActivity(R.layout.activity_bookmark) {
    private val binding by viewBinding(ActivityBookmarkBinding::bind)
    private val viewModel: BookmarkViewModel by viewModel()
    private val adapter = VerticalMealAdapter(true).apply {
        onClick = { mealId ->
            Intent(this@BookmarkActivity, RecipeActivity::class.java).apply {
                putExtra(RecipeActivity.RECIPE_EXTRA, mealId)
                startActivity(this)
            }
        }
        onBookmarkClick = { meal ->
            viewModel.deleteRecipe(meal)
            Snackbar.make(
                binding.root,
                "${meal.name} removed from bookmarks",
                Snackbar.LENGTH_SHORT
            ).show()
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

        setupToolbar()
        setupRecyclerView()
        observeFavoriteRecipes()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarBookmarks)
        binding.toolbarBookmarks.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.rvBookmarks.apply {
            layoutManager = LinearLayoutManager(this@BookmarkActivity)
            adapter = this@BookmarkActivity.adapter
        }
    }

    private fun observeFavoriteRecipes() {
        viewModel.recipes.observe(this) { favoriteMeals ->
            if (favoriteMeals.isNullOrEmpty()) {
                binding.tvMessage.visibility = View.VISIBLE
                binding.tvMessage.text = getString(R.string.no_bookmarks_found)
                binding.rvBookmarks.visibility = View.GONE
            } else {
                binding.tvMessage.visibility = View.GONE
                binding.rvBookmarks.visibility = View.VISIBLE
                adapter.submitList(favoriteMeals)
            }
        }
    }
}