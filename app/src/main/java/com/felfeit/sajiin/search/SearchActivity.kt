package com.felfeit.sajiin.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.felfeit.sajiin.R
import com.felfeit.sajiin.core.data.Resource
import com.felfeit.sajiin.core.ui.VerticalMealAdapter
import com.felfeit.sajiin.databinding.ActivitySearchBinding
import dev.androidbroadcast.vbpd.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity(R.layout.activity_search) {
    private val binding by viewBinding(ActivitySearchBinding::bind)
    private val viewModel: SearchViewModel by viewModel()
    private val adapter = VerticalMealAdapter(false).apply {
        onClick = { mealId -> }
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
        setupSearchInput()
        observeSearchResults()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarSearch)
        binding.toolbarSearch.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        binding.rvSearchMeals.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = this@SearchActivity.adapter
        }
    }

    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchQuery(s.toString())
            }
        })
    }

    private fun observeSearchResults() {
        viewModel.recipes.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading(true)
                    showMessage(false)
                    adapter.submitList(emptyList())
                }
                is Resource.Success -> {
                    showLoading(false)
                    if (resource.data.isNullOrEmpty()) {
                        showMessage(true, getString(R.string.no_results_found))
                    } else {
                        showMessage(false)
                        adapter.submitList(resource.data)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showMessage(true, resource.message ?: getString(R.string.search_error))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.circleLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvSearchMeals.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.tvMessage.visibility = View.GONE
    }

    private fun showMessage(show: Boolean, message: String? = null) {
        binding.tvMessage.visibility = if (show) View.VISIBLE else View.GONE
        binding.tvMessage.text = message
        binding.rvSearchMeals.visibility = if (show) View.GONE else View.VISIBLE
        binding.circleLoading.visibility = View.GONE
    }
}