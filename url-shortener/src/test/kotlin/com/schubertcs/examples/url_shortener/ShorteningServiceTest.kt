package com.schubertcs.examples.url_shortener

import com.schubertcs.examples.url_shortener.persistence.DbUrl
import com.schubertcs.examples.url_shortener.persistence.ShortenedUrlRepository
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.string.shouldMatch
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.data.repository.findByIdOrNull
import java.net.URI
import java.time.LocalDateTime
import java.util.stream.IntStream.range

class ShorteningServiceTest {

    private val mockUrlRepo = createMockedUrlRepo()

    @Test
    fun `same urls should return same base62 string`() {
        val url = URI.create("https://www.google.com")

        val sut = ShorteningService(mockUrlRepo)

        val result1 = sut.shorten(url)
        val result2 = sut.shorten(url)

        result1.shouldBeEqual(result2)
    }

    @Test
    fun `similar urls should return different base62 strings`() {
        val url1 = URI.create("https://www.google.com/search")
        val url2 = URI.create("https://www.google.com/find")

        val sut = ShorteningService(mockUrlRepo)

        val result1 = sut.shorten(url1)
        val result2 = sut.shorten(url2)

        result1.shouldNotBeEqual(result2)
    }

    @ParameterizedTest
    @MethodSource("createRandomUrls")
    fun `result should only contain base62 characters`(uriToTest: URI) {
        val sut = ShorteningService(mockUrlRepo)

        val result = sut.shorten(uriToTest)

        result.shouldMatch("[A-Za-z0-9]*".toRegex())
    }

    @Test
    fun `repository content should be reused`() {
        val storedDbUrl = DbUrl("abc123", URI("https://www.google.com"), LocalDateTime.now())

        every { mockUrlRepo.findByOriginalUrlOrNull(storedDbUrl.originalUrl) } returns storedDbUrl

        val sut = ShorteningService(mockUrlRepo)
        val shortenedResult = sut.shorten(storedDbUrl.originalUrl)

        shortenedResult.shouldBeEqual(storedDbUrl.shortCode)
    }

    companion object {
        @JvmStatic
        fun createRandomUrls(): List<URI> {
            return range(0, 10).mapToObj { createRandomString((5..15).random()) }.map(URI::create).toList()
        }

        @JvmStatic
        fun createRandomString(length: Int) : String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }

    private fun createMockedUrlRepo(): ShortenedUrlRepository {
        val mock = mockk<ShortenedUrlRepository>()
        every { mock.findByIdOrNull(any()) } returns null
        every { mock.findByOriginalUrlOrNull(any())} returns null
        val savedUrl = slot<DbUrl>()
        every { mock.save(capture(savedUrl)) } answers { savedUrl.captured }
        return mock
    }
}
