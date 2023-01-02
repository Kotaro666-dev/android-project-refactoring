/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.CustomAdapter
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import jp.co.yumemi.android.code_check.model.GithubRepository
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        binding = FragmentSearchBinding.bind(view)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(context, layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: GithubRepository) {
                navigateToSearchResultsDetail(item)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener { searchInput, action, _ ->
                val isSearchAction = action == EditorInfo.IME_ACTION_SEARCH
                if (!isSearchAction) {
                    return@setOnEditorActionListener false
                }
                val searchKeyword = searchInput.text.toString()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.searchGithubRepositories(searchKeyword)
                }
                return@setOnEditorActionListener true
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        viewModel.githubRepositories.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun navigateToSearchResultsDetail(item: GithubRepository) {
        val action = SearchFragmentDirections
            .actionNavigateToFragmentSearchResultsDetail(githubRepository = item)
        findNavController().navigate(action)
    }
}