/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchScreenBinding
import kotlinx.coroutines.launch

class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {

    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var binding: FragmentSearchScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        binding = FragmentSearchScreenBinding.bind(view)
        viewModel = ViewModelProvider(this)[SearchScreenViewModel::class.java]

        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(context, layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: GithubRepository) {
                navigateToSearchResultsDetailScreen(item)
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

    private fun navigateToSearchResultsDetailScreen(item: GithubRepository) {
        val action = SearchScreenFragmentDirections
            .actionNavigateToFragmentSearchResultsDetailScreen(githubRepository = item)
        findNavController().navigate(action)
    }
}