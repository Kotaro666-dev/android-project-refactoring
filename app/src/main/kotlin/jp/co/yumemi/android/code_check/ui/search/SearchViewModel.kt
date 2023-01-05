/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.model.GithubRepositoryData
import jp.co.yumemi.android.code_check.usecase.SearchResultsDetailUseCase
import kotlinx.coroutines.*
import java.util.*
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: SearchResultsDetailUseCase) :
    ViewModel() {
    private val _githubRepositories = MutableLiveData<List<GithubRepositoryData>>()
    val githubRepositories: LiveData<List<GithubRepositoryData>> get() = _githubRepositories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasError = MutableLiveData(false)
    val hasError: LiveData<Boolean> get() = _hasError

    suspend fun searchGithubRepositories(searchKeyword: String) {
        viewModelScope.launch {
            try {
                _hasError.postValue(false)
                _isLoading.postValue(true)

                Log.d("検索した日時", Date().toString())
                val githubRepositories = useCase.get(searchKeyword)

                _githubRepositories.postValue(githubRepositories)
                _isLoading.postValue(false)
                return@launch
            } catch (e: Exception) {
                _githubRepositories.postValue(emptyList())
                _isLoading.postValue(false)
                _hasError.postValue(true)
                return@launch
            }
        }
    }
}