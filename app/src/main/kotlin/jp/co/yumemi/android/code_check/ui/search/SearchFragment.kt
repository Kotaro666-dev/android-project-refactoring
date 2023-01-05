/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import jp.co.yumemi.android.code_check.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.utilities.CustomAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        val customAdapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: GithubRepositoryData) {
                navigateToSearchResultsDetail(item)
            }
        })
        setRecyclerView(customAdapter = customAdapter)

        binding.searchInputText
            .setOnEditorActionListener { searchInput, action, _ ->
                val isSearchAction = action == EditorInfo.IME_ACTION_SEARCH
                if (!isSearchAction) {
                    return@setOnEditorActionListener false
                }
                val searchKeyword = searchInput.text.toString()
                hideSoftKeyboard(searchInput)
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.searchGithubRepositories(searchKeyword)
                }
                return@setOnEditorActionListener true
            }

        viewModel.githubRepositories.observe(viewLifecycleOwner, {
            customAdapter.submitList(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            binding.composableCircularProgressIndicator.apply {
                // Dispose of the Composition when the view's LifecycleOwner is destroyed
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    MaterialTheme {
                        if (isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        })

        viewModel.hasError.observe(viewLifecycleOwner, { hasError ->
            if (hasError) {
                binding.errorMessageText.visibility = View.VISIBLE
            } else {
                binding.errorMessageText.visibility = View.GONE
            }
        })
    }

    private fun setRecyclerView(customAdapter: CustomAdapter) {
        val context = requireContext()
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(context, layoutManager.orientation)
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = customAdapter
        }
    }

    private fun hideSoftKeyboard(searchInput: TextView) {
        val inputMethodManager =
            this.requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            searchInput.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun navigateToSearchResultsDetail(item: GithubRepositoryData) {
        val action = SearchFragmentDirections
            .actionNavigateToFragmentSearchResultsDetail(githubRepositoryData = item)
        findNavController().navigate(action)
    }
}