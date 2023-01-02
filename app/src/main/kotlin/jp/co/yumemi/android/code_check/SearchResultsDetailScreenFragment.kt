/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentSearchResultsDetailScreenBinding

class SearchResultsDetailScreenFragment : Fragment(R.layout.fragment_search_results_detail_screen) {

    private val args: SearchResultsDetailScreenFragmentArgs by navArgs()

    private var binding: FragmentSearchResultsDetailScreenBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentSearchResultsDetailScreenBinding.bind(view)

        val githubRepository = args.githubRepository

        _binding.ownerIconView.load(githubRepository.ownerIconUrl);
        _binding.nameView.text = githubRepository.name;
        _binding.languageView.text = githubRepository.language;
        _binding.starsView.text = "${githubRepository.stargazersCount} stars";
        _binding.watchersView.text = "${githubRepository.watchersCount} watchers";
        _binding.forksView.text = "${githubRepository.forksCount} forks";
        _binding.openIssuesView.text = "${githubRepository.openIssuesCount} open issues";
    }
}
