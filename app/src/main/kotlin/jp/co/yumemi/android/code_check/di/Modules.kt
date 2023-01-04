package jp.co.yumemi.android.code_check.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.data.repository.GithubRepository
import jp.co.yumemi.android.code_check.data.repository.GithubRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGithubRepository(githubRepository: GithubRepositoryImpl): GithubRepository
}