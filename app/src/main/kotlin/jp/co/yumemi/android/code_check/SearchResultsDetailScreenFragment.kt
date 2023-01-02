/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.databinding.FragmentSearchResultsDetailScreenBinding

class SearchResultsDetailScreenFragment : Fragment(R.layout.fragment_search_results_detail_screen) {

    private val args: SearchResultsDetailScreenFragmentArgs by navArgs()
    private lateinit var binding: FragmentSearchResultsDetailScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchResultsDetailScreenBinding.bind(view)

        val githubRepository = args.githubRepository

        binding.ownerIconView.load(githubRepository.ownerIconUrl);
        binding.nameView.text = githubRepository.name;
        binding.languageView.text = githubRepository.language;
        binding.starsView.text = "${githubRepository.stargazersCount} stars";
        binding.watchersView.text = "${githubRepository.watchersCount} watchers";
        binding.forksView.text = "${githubRepository.forksCount} forks";
        binding.openIssuesView.text = "${githubRepository.openIssuesCount} open issues";
    }
}
