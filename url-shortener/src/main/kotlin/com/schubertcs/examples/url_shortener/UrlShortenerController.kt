package com.schubertcs.examples.url_shortener

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class UrlShortenerController(
    private val shorteningService: ShorteningService,
    private val baseUrlService: BaseUrlService
) {

    @PostMapping(
        path = ["/shorten"],
        consumes = ["application/json"])
    fun shortenUrl(@RequestBody input: ShortenUrlInput): ResponseEntity<ShortenUrlOutput> {
        val baseUrl = baseUrlService.getBaseUrl()
        val encodedUrl = shorteningService.shorten(input.url)
        val result = baseUrl.resolve(encodedUrl)
        return ResponseEntity.ok().body(ShortenUrlOutput(shortUrl = result))
    }
}

data class ShortenUrlInput(
    val url: URI
)

data class ShortenUrlOutput(
    val shortUrl: URI
)
