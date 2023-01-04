package jp.co.yumemi.android.code_check.ui.search

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.co.yumemi.android.code_check.launchFragmentInHiltContainer
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }


    @Test
    fun `SearchFragmentでUIテストできる状態にする`() {
        launchFragmentInHiltContainer<SearchFragment> {
            assertEquals(2 + 2, 4)
        }
    }
}
