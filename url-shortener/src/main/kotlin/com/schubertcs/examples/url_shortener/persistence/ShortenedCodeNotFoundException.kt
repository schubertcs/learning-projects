package com.schubertcs.examples.url_shortener.persistence

class ShortenedCodeNotFoundException(shortenedCode: String) :
    Exception("Could not find code '${shortenedCode}' in the database") {
}