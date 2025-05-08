package com.schubertcs.examples.url_shortener.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.net.URI

interface ShortenedUrlRepository : JpaRepository<DbUrl, String> {
    fun findByOriginalUrlOrNull(originalUrl: URI): DbUrl?
}