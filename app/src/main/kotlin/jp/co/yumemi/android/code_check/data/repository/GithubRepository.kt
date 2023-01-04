package jp.co.yumemi.android.code_check.data.repository

import android.util.Log
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.data.api.GithubApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface GithubRepository {
    suspend fun tryRequestGithubRepositories(searchKeyword: String): HttpResponse
}

@Singleton
class GithubRepositoryImpl @Inject constructor(private val githubApiClient: GithubApiClient) :
    GithubRepository {

    override suspend fun tryRequestGithubRepositories(searchKeyword: String): HttpResponse {
        return withContext(Dispatchers.IO) {
            try {
                githubApiClient.getRepositories(searchKeyword)
            } catch (e: Exception) {
                Log.e("[Exception]tryRequestGithubRepositories", e.toString())
                throw e
            }
        }
    }
}