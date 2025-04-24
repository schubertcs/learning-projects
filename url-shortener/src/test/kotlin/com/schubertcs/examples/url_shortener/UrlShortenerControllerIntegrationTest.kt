package com.schubertcs.examples.url_shortener

import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.uri.shouldHaveHost
import io.kotest.matchers.uri.shouldHavePath
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import java.net.URI


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerControllerIntegrationTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `shorten should return shortened url`(){
        val body = HttpEntity<ShortenUrlInput>(ShortenUrlInput(url=URI.create("https://example.com/some/path")))
        val entity = restTemplate.postForEntity<ShortenUrlOutput>("/shorten", body)

        entity.statusCode.value().shouldBe(200)
        val shortUrl = entity.body!!.shortUrl
        shortUrl.shouldHaveHost("localhost")
        shortUrl.path.shouldMatch("/[A-Za-z0-9]*".toRegex())
    }
}