/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchScreenBinding
import kotlinx.coroutines.launch

class SearchScreenFragment : Fragment(R.layout.fragment_search_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        val binding = FragmentSearchScreenBinding.bind(view)
        val viewModel = SearchScreenViewModel()

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

val diffCallback = object : DiffUtil.ItemCallback<GithubRepository>() {
    override fun areItemsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GithubRepository, newItem: GithubRepository): Boolean {
        return oldItem == newItem
    }

}

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GithubRepository, CustomAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: GithubRepository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repository_name_view) as TextView).text =
            item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}
