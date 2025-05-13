package com.schubertcs.examples.url_shortener.persistence

import jakarta.persistence.*


@Entity
@Table(
    indexes = [Index(name = "idx_original_url", columnList = "originalUrl")]
)
class DbUrl(
    @Id
    @Column(nullable = false)
    val shortCode: String,
    @Column(nullable = false, unique = true)
    val originalUrl: String,
)