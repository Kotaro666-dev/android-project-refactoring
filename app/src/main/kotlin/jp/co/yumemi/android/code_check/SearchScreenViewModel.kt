/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import kotlinx.coroutines.*
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

const val GITHUB_SEARCH_API_ENDPOINT = "https://api.github.com/search/repositories"
const val GITHUB_SEARCH_API_HEADER_ACCEPT_VALUE = "application/vnd.github.v3+json"

class SearchScreenViewModel(
    val context: Context
) : ViewModel() {
    private val client = HttpClient(Android)

    fun searchGithubRepositories(searchKeyword: String): List<GithubRepository> = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            val response = requestGithubRepositories(searchKeyword)
            // TODO: レスポンスエラーチェックする
            val jsonItems = parseResponseBody(response) ?: return@withContext listOf()
            val githubRepositories = createGithubRepositoryList(jsonItems)
            setLastSearchDate()
            return@withContext githubRepositories
        }
    }

    private suspend fun requestGithubRepositories(searchKeyword: String): HttpResponse {
        return client.get(GITHUB_SEARCH_API_ENDPOINT) {
            header("Accept", GITHUB_SEARCH_API_HEADER_ACCEPT_VALUE)
            parameter("q", searchKeyword)
        }
    }

    private suspend fun parseResponseBody(response: HttpResponse): JSONArray? {
        // TODO: 例外発生する可能性あり
        val jsonBody = JSONObject(response.receive<String>())
        return jsonBody.optJSONArray("items")
    }

    private fun extractGithubRepositoryData(jsonItem: JSONObject): GithubRepository {
        val name = jsonItem.optString("full_name")
        val ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
        val language = jsonItem.optString("language")
        val stargazersCount = jsonItem.optLong("stargazers_count")
        val watchersCount = jsonItem.optLong("watchers_count")
        val forksCount = jsonItem.optLong("forks_conut")
        val openIssuesCount = jsonItem.optLong("open_issues_count")
        return GithubRepository(
            name = name,
            ownerIconUrl = ownerIconUrl,
            language = context.getString(R.string.written_language, language),
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount
        )
    }

    private fun createGithubRepositoryList(jsonItems: JSONArray): List<GithubRepository> {
        val githubRepositories = mutableListOf<GithubRepository>()
        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i) ?: continue
            val githubRepository = extractGithubRepositoryData(jsonItem)
            githubRepositories.add(githubRepository)
        }
        return githubRepositories.toList()
    }

    private fun setLastSearchDate() {
        lastSearchDate = Date()
    }
}

@Parcelize
data class GithubRepository(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable