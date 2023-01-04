package jp.co.yumemi.android.code_check.usecase

import android.util.Log
import io.ktor.client.call.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.data.repository.GithubRepository
import jp.co.yumemi.android.code_check.model.GithubRepositoryData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Collections.emptyList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultsDetailUseCase @Inject constructor(private val githubRepository: GithubRepository) {

    suspend fun get(searchKeyword: String): List<GithubRepositoryData> {
        try {
            val response = githubRepository.tryRequestGithubRepositories(searchKeyword)
            val jsonItems = tryParseResponseBody(response) ?: return emptyList()
            return createGithubRepositoryList(jsonItems)
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private suspend fun tryParseResponseBody(response: HttpResponse): JSONArray? {
        return try {
            val jsonBody = JSONObject(response.receive<String>())
            jsonBody.optJSONArray("items")
        } catch (e: JSONException) {
            Log.e("[JSONException]tryParseResponseBody", e.toString())
            throw e
        }
    }

    private fun extractGithubRepositoryData(jsonItem: JSONObject): GithubRepositoryData {
        val name = jsonItem.optString("full_name")
        val ownerIconUrl = jsonItem.optJSONObject("owner")?.optString("avatar_url") ?: ""
        val language = jsonItem.optString("language")
        val stargazersCount = jsonItem.optLong("stargazers_count")
        val watchersCount = jsonItem.optLong("watchers_count")
        val forksCount = jsonItem.optLong("forks_count")
        val openIssuesCount = jsonItem.optLong("open_issues_count")
        return GithubRepositoryData(
            name = name,
            ownerIconUrl = ownerIconUrl,
            language = "Written in $language",
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount
        )
    }

    private fun createGithubRepositoryList(jsonItems: JSONArray): List<GithubRepositoryData> {
        val githubRepositories = mutableListOf<GithubRepositoryData>()
        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.optJSONObject(i) ?: continue
            val githubRepositoryData = extractGithubRepositoryData(jsonItem)
            githubRepositories.add(githubRepositoryData)
        }
        return githubRepositories.toList()
    }
}