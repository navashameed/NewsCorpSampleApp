package com.newscorp.sampleapp.ui.headlines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newscorp.sampleapp.CoroutineTestRule
import com.newscorp.sampleapp.network.NetworkHttpException
import com.newscorp.sampleapp.network.model.ArticleResponse
import com.newscorp.sampleapp.repository.NewsCorpRepositoryImpl
import com.newscorp.sampleapp.repository.model.Article
import com.newscorp.sampleapp.repository.model.toArticlesUIModel
import com.newscorp.sampleapp.utils.ViewStateGeneric
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class NewsHeadlinesViewModelTest {
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    lateinit var newsCorpRepository: NewsCorpRepositoryImpl

    private lateinit var artclesList: List<Article>

    private lateinit var viewModel: NewsHeadlinesViewModel

    private val testUtils = TestUtils()

    private var mockArticlesResponseData: ArticleResponse? = null

    @Before
    fun initValues() {
        mockArticlesResponseData = testUtils.readJsonResource(
            "headlines.json",
            ArticleResponse::class.java
        )

        artclesList = mockArticlesResponseData?.articles?.map { it.toArticlesUIModel() }!!
        runBlocking {
            whenever(newsCorpRepository.getNewsHeadLines("")).doReturn(
                artclesList
            )
        }
        viewModel =
            NewsHeadlinesViewModel(newsCorpRepository, coroutinesTestRule.testDispatcher)
    }

    @Test
    fun `WHEN fetchNewsArticles AND Response Success THEN viewstate is populated with artcles data`() {
        runTest {
            viewModel.fetchNewsArticles()
            assertEquals(ViewStateGeneric.Success, viewModel.viewState.value)
            val listOfViewItems = viewModel.headlinesList.value!!

            assertEquals(20, listOfViewItems.size)

            val newsItemView = listOfViewItems[1]
            assertEquals("Sat 13 Aug", newsItemView.date)

            // verify item click
            newsItemView.onItemClick.invoke()
            assertEquals(viewModel.navigateToNewsDetails.value?.peekContent(), newsItemView.article)

            val article = newsItemView.article

            assertEquals("Savannah Meacham", article.author)
            assertTrue(
                article.url!!.startsWith("https://www.9news.com.au/national")
            )
            assertEquals(
                "https://imageresizer.static9.net.au/S8tvPvQ4P_8MJvgnB2T9OZWgO2I=/1200x628/smart/https%3A%2F%2Fprod.static9.net.au%2Ffs%2F2ad37ce5-8539-4bcf-94e8-ba03e3ae2778",
                article.urlToImage
            )
            assertTrue { article.title!!.contains("Australia's attorneys-general to ramp up action on coercive control") }
            assertTrue { article.description!!.contains("the pattern of abusive behaviour designed to create power and dominance") }
            assertEquals("Sat Aug 13 01:58:00 AEST 2022", article.publishedAt.toString())
            assertEquals("9News", article.articleSourceName)
            assertNull(article.articleSourceId)

        }
    }

    @Test
    fun `WHEN fetchNewsArticles AND Response Success THEN repository getNewsHeadLines is invoked`() {
        runTest {
            viewModel.fetchNewsArticles()
            verify(newsCorpRepository).getNewsHeadLines("")
            assertEquals(ViewStateGeneric.Success, viewModel.viewState.value)
        }
    }

    @Test
    fun `WHEN fetchNewsArticles AND Error happened THEN ViewState is set to Error`() = runTest {
        whenever(newsCorpRepository.getNewsHeadLines("")).doAnswer {
            throw NetworkHttpException.create(401)
        }

        viewModel.fetchNewsArticles()
        verify(newsCorpRepository).getNewsHeadLines("")
        assertEquals(ViewStateGeneric.Error(""), viewModel.viewState.value)
    }

    @Test
    fun `WHEN fetchNewsArticles AND Exception happened THEN ViewState is set to Error`() = runTest {
        whenever(newsCorpRepository.getNewsHeadLines("")).doAnswer {
            throw Exception()
        }

        viewModel.fetchNewsArticles()
        verify(newsCorpRepository).getNewsHeadLines("")
        assertTrue { viewModel.viewState.value is ViewStateGeneric.Error }
    }


    @Test
    fun `WHEN onArticleItemClick THEN navigateToNewsDetails is invoked`() {
        runTest {
            val article = artclesList[0]
            viewModel.onNewsItemClick(article)
            assertEquals(article, viewModel.navigateToNewsDetails.value?.peekContent())
        }
    }
}