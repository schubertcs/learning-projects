package com.schubertcs.examples.url_shortener

import com.github.f4b6a3.uuid.UuidCreator
import com.github.f4b6a3.uuid.codec.base.Base62Codec
import com.schubertcs.examples.url_shortener.persistence.DbUrl
import com.schubertcs.examples.url_shortener.persistence.ShortenedCodeNotFoundException
import com.schubertcs.examples.url_shortener.persistence.ShortenedUrlRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.net.URI
import java.time.LocalDateTime

@Service
class ShorteningService (
    private val urlRepository: ShortenedUrlRepository,
) {
    fun shorten(uriToShorten:URI) : String {
        val uriString = uriToShorten.toString()
        val maybeStoredResult = urlRepository.findByOriginalUrl(uriString)
        if (maybeStoredResult != null) {
            return maybeStoredResult.shortCode
        }
        val uuid = UuidCreator.getNameBasedSha1(uriString)
        val encoded = Base62Codec.INSTANCE.encode(uuid)
        val storedResult =
            urlRepository.save(DbUrl(shortCode = encoded, originalUrl = uriString))
        return storedResult.shortCode
    }

    fun expand(shortenedCode: String) : URI {
        val urlFromDb = urlRepository.findByIdOrNull(shortenedCode) ?: throw ShortenedCodeNotFoundException(shortenedCode)
        return URI.create(urlFromDb.originalUrl)
    }
}