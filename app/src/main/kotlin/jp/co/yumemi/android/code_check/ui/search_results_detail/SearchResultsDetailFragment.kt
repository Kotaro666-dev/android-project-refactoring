/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
@file:Suppress("unused")

package jp.co.yumemi.android.code_check.ui.search_results_detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSearchResultsDetailBinding


@AndroidEntryPoint
class SearchResultsDetailFragment : Fragment(R.layout.fragment_search_results_detail) {

    private val args: SearchResultsDetailFragmentArgs by navArgs()
    private var _binding: FragmentSearchResultsDetailBinding? = null
    private val binding get() = _binding!!

    // ビジネスロジックを実装する際には、以下の viewModel を使ってください
    private val viewModel: SearchResultsDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchResultsDetailBinding.bind(view)

        bindGithubRepositoryData()
        enableBackButtonOnAppBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            // Deprecated: 非推奨となっているため、将来移行する
            // 参考資料: https://developer.android.com/jetpack/androidx/releases/activity#1.4.0-alpha01
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun bindGithubRepositoryData() {
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

    private fun enableBackButtonOnAppBar() {
        val activity = activity as AppCompatActivity
        val actionBar: ActionBar? = activity.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        // Deprecated: 非推奨となっているため、将来移行する
        // 参考資料: https://developer.android.com/jetpack/androidx/releases/activity#1.4.0-alpha01
        setHasOptionsMenu(true)
    }
}
