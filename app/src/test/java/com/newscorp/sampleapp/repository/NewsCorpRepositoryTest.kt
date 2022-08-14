package com.newscorp.sampleapp.repository

import com.newscorp.sampleapp.db.NewsCorpDb
import com.newscorp.sampleapp.network.NetworkHub
import com.newscorp.sampleapp.network.model.ArticleResponse
import com.newscorp.sampleapp.network.model.SourceResponse
import com.newscorp.sampleapp.ui.headlines.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class NewsCorpRepositoryTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var mockNetworkHub: NetworkHub

    @Mock
    lateinit var mockNewsCorpDb: NewsCorpDb

    private lateinit var newsCorpRepository: NewsCorpRepositoryImpl

    private var mockArticlesResponseData: ArticleResponse? = null

    private var mockSourcesResponseData: SourceResponse? = null

    private val testUtils = TestUtils()

    @Before
    fun initValues() {
        mockArticlesResponseData = testUtils.readJsonResource(
            "headlines.json",
            ArticleResponse::class.java
        )

        mockSourcesResponseData = testUtils.readJsonResource(
            "sources.json",
            SourceResponse::class.java
        )

        runBlocking {
            whenever(mockNetworkHub.getHeadlines(any())).doReturn(
                mockArticlesResponseData
            )

            whenever(mockNetworkHub.getSources()).doReturn(
                mockSourcesResponseData
            )
        }
        newsCorpRepository = NewsCorpRepositoryImpl(mockNetworkHub, mockNewsCorpDb)
    }

    @Test
    fun `WHEN getNewsHeadLines AND Response Success THEN network getHeadlines is invoked and data is returned`() =
        runTest {
            val articlesList = newsCorpRepository.getNewsHeadLines("")!!

            verify(mockNetworkHub).getHeadlines("")

            val article = articlesList[1]

            assertEquals(20, articlesList.size)

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

    @Test
    fun `WHEN getNewsSources AND Response Success THEN network getSources is invoked and data is returned`() =
        runTest {
            val sourceList = newsCorpRepository.getNewsSources()!!

            verify(mockNetworkHub).getSources()

            val source = sourceList[0]

            assertEquals(4, sourceList.size)

            assertEquals("abc-news-au", source.id)
            assertEquals("ABC News (AU)", source.name)
            assertTrue { source.description!!.contains("Australia's most trusted source of local, national") }
            assertEquals("general", source.category)
            assertEquals("au", source.country)
            assertEquals("http://www.abc.net.au/news", source.url)
            assertEquals("en", source.language)

        }
}
