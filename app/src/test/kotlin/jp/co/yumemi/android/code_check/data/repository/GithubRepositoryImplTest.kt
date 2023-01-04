package jp.co.yumemi.android.code_check.data.repository

import jp.co.yumemi.android.code_check.data.api.GithubApiClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GithubRepositoryImplTest {

    private lateinit var githubApiClient: GithubApiClient
    private lateinit var githubRepository: GithubRepositoryImpl

    @Before
    fun setUp() {
        githubApiClient = GithubApiClient()
        githubRepository = GithubRepositoryImpl(githubApiClient)
    }

    @After
    fun tearDown() {
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `【正常系】キーワード「Flutter」で検索した場合、APIサーバから正常なレスポンスが返却される`() = runBlocking {
        val response = githubRepository.tryRequestGithubRepositories("Flutter")
        Assert.assertEquals(200, response.status.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `【正常系】リポジトリに存在しないキーワードで検索した場合でも、APIサーバから正常なレスポンスが返却される`() = runBlocking {
        val keywordForNonRepository = "abcdefghijklmnopqrstuvwxyzabcd"
        val response = githubRepository.tryRequestGithubRepositories(keywordForNonRepository)
        Assert.assertEquals(200, response.status.value)
    }

    @ExperimentalCoroutinesApi
    @Test(expected = Exception::class)
    fun `【準正常系】空文字のキーワードで検索した場合、APIサーバから失敗のレスポンスが返却され、例外が発生する`(): Unit = runBlocking {
        val emptyKeyword = ""
        githubRepository.tryRequestGithubRepositories(emptyKeyword)
    }

    @ExperimentalCoroutinesApi
    @Test(expected = Exception::class)
    fun `【準正常系】256 文字超のキーワードで検索した場合、APIサーバから失敗のレスポンスが返却され、例外が発生する`(): Unit = runBlocking {
        val keywordWith257Letters =
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvw"
        githubRepository.tryRequestGithubRepositories(keywordWith257Letters)
    }
}