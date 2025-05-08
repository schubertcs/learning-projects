package com.schubertcs.examples.url_shortener.persistence

import jakarta.persistence.*
import java.net.URI
import java.time.LocalDateTime

@Entity
@Table(
    indexes = arrayOf(
        Index(name = "idx_original_url", columnList = "originalUrl")
    )
)
class DbUrl(
    @Id
    @Column(nullable = false)
    val shortCode: String,
    @Column(nullable = false, unique = true)
    val originalUrl: URI,
    @Column(nullable = false)
    val createdAt: LocalDateTime,
)