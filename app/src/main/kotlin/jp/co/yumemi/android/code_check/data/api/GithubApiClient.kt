package jp.co.yumemi.android.code_check.data.api

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubApiClient {
    private val client = HttpClient(Android)

    suspend fun getRepositories(searchKeyword: String): HttpResponse {
        return withContext(Dispatchers.IO) {
            client.get(GITHUB_SEARCH_API_ENDPOINT) {
                header(
                    "Accept",
                    GITHUB_SEARCH_API_HEADER_ACCEPT_VALUE
                )
                parameter("q", searchKeyword)
            }
        }
    }

    companion object {
        const val GITHUB_SEARCH_API_ENDPOINT = "https://api.github.com/search/repositories"
        const val GITHUB_SEARCH_API_HEADER_ACCEPT_VALUE = "application/vnd.github.v3+json"
    }
}

