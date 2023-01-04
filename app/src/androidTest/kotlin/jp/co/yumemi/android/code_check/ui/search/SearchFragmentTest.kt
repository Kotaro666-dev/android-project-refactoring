package jp.co.yumemi.android.code_check.ui.search

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.launchFragmentInHiltContainer
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertTrue
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
    fun `SearchFragmentでUIテストを開始できる状態である`() {
        launchFragmentInHiltContainer<SearchFragment> {
            assertEquals(2 + 2, 4)
        }
    }

    @Test
    fun `検索バーが表示されている`() {
        launchFragmentInHiltContainer<SearchFragment> {
            val searchBar = this.view?.findViewById<MaterialCardView>(R.id.search_bar)
            assertTrue(searchBar?.isShown ?: false)
        }
    }

    @Test
    fun `検索バーのヒントテキストが想定しているものである`() {
        launchFragmentInHiltContainer<SearchFragment> {
            val expectedHintText = "GitHub のリポジトリを検索できるよー"
            val displayedHintText = this.view?.findViewById<TextInputEditText>(
                R.id.search_input_text
            )?.hint.toString()
            assertEquals(
                expectedHintText, displayedHintText
            )
        }
    }
}
