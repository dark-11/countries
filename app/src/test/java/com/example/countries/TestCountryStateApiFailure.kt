package com.example.countries

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.countries.datasource.CountryDataSource
import com.example.countries.repository.CountryRepository
import com.example.countries.util.ResponseFileReader
import com.example.network.service.CountryService
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class TestCountryStateApiFailure {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()

    private lateinit var dataSource: CountryDataSource

    private lateinit var repository: CountryRepository

    private lateinit var mockedResponse: String

    private val gson = GsonBuilder()
                          .setLenient()
                          .create()

    @Before
    fun init() {
        server.start(8000)

        var BASE_URL = server.url("https://countriesnow.space/api/v0.1/countries/").toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(CountryService::class.java)
        dataSource = CountryDataSource(service)
        repository = CountryRepository(dataSource)
    }

    @Test
    fun testApiFailure() {
        mockedResponse = ResponseFileReader("country/failure.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.baseRepositoryResponse { dataSource.getData() }.data}
        val json = gson.toJson(response)

        val resultResponse = JsonParser.parseString(json)
        val expectedresponse = JsonParser.parseString(mockedResponse)

        Assert.assertNotNull(response)
        Assert.assertNotEquals(expectedresponse,resultResponse)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}