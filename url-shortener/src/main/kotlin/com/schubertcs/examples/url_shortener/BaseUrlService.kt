package com.schubertcs.examples.url_shortener

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.net.URI

@Service
class BaseUrlService (
    private val incomingRequest: HttpServletRequest
) {
    fun getBaseUrl() : URI {
        val scheme: String = incomingRequest.scheme
        val serverName: String = incomingRequest.serverName
        val serverPort: Int = incomingRequest.serverPort

        return URI.create("$scheme://$serverName:$serverPort")
    }
}