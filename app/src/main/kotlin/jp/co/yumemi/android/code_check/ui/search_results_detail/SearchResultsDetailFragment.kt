/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search_results_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSearchResultsDetailBinding

class SearchResultsDetailFragment : Fragment(R.layout.fragment_search_results_detail) {

    private val args: SearchResultsDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentSearchResultsDetailBinding
    private lateinit var viewModel: SearchResultsDetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchResultsDetailBinding.bind(view)
        viewModel = ViewModelProvider(this)[SearchResultsDetailViewModel::class.java]

        val githubRepositoryData = args.githubRepositoryData

        binding.ownerIconView.load(githubRepositoryData.ownerIconUrl)
        binding.nameView.text = githubRepositoryData.name
        binding.languageView.text = githubRepositoryData.language
        binding.starsView.text =
            getString(R.string.stars_count, githubRepositoryData.stargazersCount.toString())
        binding.watchersView.text =
            getString(R.string.watchers_count, githubRepositoryData.watchersCount.toString())
        binding.forksView.text =
            getString(R.string.forks_count, githubRepositoryData.forksCount.toString())
        binding.openIssuesView.text =
            getString(R.string.open_issues_count, githubRepositoryData.openIssuesCount.toString())
    }
}
