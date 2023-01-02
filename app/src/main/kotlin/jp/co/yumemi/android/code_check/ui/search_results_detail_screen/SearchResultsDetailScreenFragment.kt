/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search_results_detail_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSearchResultsDetailScreenBinding

class SearchResultsDetailScreenFragment : Fragment(R.layout.fragment_search_results_detail_screen) {

    private val args: SearchResultsDetailScreenFragmentArgs by navArgs()
    private lateinit var binding: FragmentSearchResultsDetailScreenBinding
    private lateinit var viewModel: SearchResultsDetailScreenViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchResultsDetailScreenBinding.bind(view)
        viewModel = ViewModelProvider(this)[SearchResultsDetailScreenViewModel::class.java]

        val githubRepository = args.githubRepository

        binding.ownerIconView.load(githubRepository.ownerIconUrl)
        binding.nameView.text = githubRepository.name
        binding.languageView.text = githubRepository.language
        binding.starsView.text =
            getString(R.string.stars_count, githubRepository.stargazersCount.toString())
        binding.watchersView.text =
            getString(R.string.watchers_count, githubRepository.watchersCount.toString())
        binding.forksView.text =
            getString(R.string.forks_count, githubRepository.forksCount.toString())
        binding.openIssuesView.text =
            getString(R.string.open_issues_count, githubRepository.openIssuesCount.toString())
    }
}
