package jp.co.yumemi.android.code_check.usecase

import jp.co.yumemi.android.code_check.data.api.GithubApiClient
import jp.co.yumemi.android.code_check.data.repository.GithubRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchResultsDetailUseCaseTest {

    private lateinit var searchResultsDetailUseCase: SearchResultsDetailUseCase
    private lateinit var githubRepository: GithubRepositoryImpl
    private lateinit var githubApiClient: GithubApiClient

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        githubApiClient = GithubApiClient()
        githubRepository = GithubRepositoryImpl(githubApiClient)
        searchResultsDetailUseCase = SearchResultsDetailUseCase(githubRepository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `【正常系】キーワード「Flutter」で検索した場合、GithubRepositoryデータが 30 件返却される`() = runBlocking {
        val githubRepositories = searchResultsDetailUseCase.get("flutter")
        val expectedLength = 30
        Assert.assertTrue(githubRepositories.size == expectedLength)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `【【正常系】リポジトリに存在しないキーワードで検索した場合、GithubRepositoryデータが 0 件返却される`() = runBlocking {
        val keywordForNonRepository = "abcdefghijklmnopqrstuvwxyzabcd"
        val githubRepositories = searchResultsDetailUseCase.get(keywordForNonRepository)
        val expectedLength = 0
        Assert.assertTrue(githubRepositories.size == expectedLength)
    }

    @ExperimentalCoroutinesApi
    @Test(expected = Exception::class)
    fun `【準正常系】空文字のキーワードで検索した場合、例外が発生する`(): Unit = runBlocking {
        val emptyKeyword = ""
        searchResultsDetailUseCase.get(emptyKeyword)
    }

    @ExperimentalCoroutinesApi
    @Test(expected = Exception::class)
    fun `【準正常系】256 文字超のキーワードで検索した場合、例外が発生する`(): Unit = runBlocking {
        val keywordWith257Letters =
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvw"
        searchResultsDetailUseCase.get(keywordWith257Letters)
    }
}