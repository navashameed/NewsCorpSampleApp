package com.newscorp.sampleapp.network

import com.newscorp.sampleapp.network.model.ArticleResponse
import com.newscorp.sampleapp.network.model.SourceResponse
import com.newscorp.sampleapp.network.retrofit.NewsApiInterface
import com.newscorp.sampleapp.ui.headlines.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class NetworkHubImplTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var newsApiInterface: NewsApiInterface

    @Mock
    lateinit var mockResponse: Response<ArticleResponse>

    @Mock
    lateinit var mockSourcesResponse: Response<SourceResponse>

    private lateinit var networkHub: NetworkHubImpl

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
            whenever(mockResponse.body()).doReturn(mockArticlesResponseData)
            whenever(newsApiInterface.getHeadlines()).doReturn(mockResponse)

            whenever(mockSourcesResponse.body()).doReturn(mockSourcesResponseData)
            whenever(newsApiInterface.getSources()).doReturn(mockSourcesResponse)
        }
        networkHub = NetworkHubImpl(newsApiInterface)
    }

    @Test
    fun `WHEN getArticleHeadLines AND Response Success THEN repository getHeadlines is invoked and data is returned`() =
        runTest {
            val headlinesResponse = networkHub.getHeadlines("")
            verify(newsApiInterface).getHeadlines()

            val articles = headlinesResponse?.articles!!
            val article = articles[1]

            assertEquals(20, articles.size)
            assertEquals(36, headlinesResponse.totalResults)

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
            assertEquals("9News", article.source?.name)
            assertNull(article.source?.id)
        }

    @Test
    fun `WHEN getSources AND Response Success THEN repository getSources is invoked and data is returned`() =
        runTest {
            val sourcesResponseData = networkHub.getSources()
            verify(newsApiInterface).getSources()

            val sources = sourcesResponseData?.sources!!

            assertEquals(4, sources.size)

            val source = sources[0]
            assertEquals("abc-news-au", source.id)
            assertEquals("ABC News (AU)", source.name)
            assertTrue { source.description!!.contains("Australia's most trusted source of local, national") }
            assertEquals("general", source.category)
            assertEquals("au", source.country)
            assertEquals("http://www.abc.net.au/news", source.url)
            assertEquals("en", source.language)
        }

}