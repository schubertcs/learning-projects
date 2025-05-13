package com.schubertcs.examples.url_shortener.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ShortenedUrlRepository : JpaRepository<DbUrl, String> {
    fun findByOriginalUrl(originalUrl: String): DbUrl?
}