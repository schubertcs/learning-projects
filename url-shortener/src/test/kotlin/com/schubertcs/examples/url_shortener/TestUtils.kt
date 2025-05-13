package com.schubertcs.examples.url_shortener

import org.apache.hc.client5.http.classic.HttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Bean
@Qualifier("forTests")
fun nonRedirectingRestTemplate(): RestTemplate {
    val httpClient: HttpClient = HttpClients.custom()
        .disableRedirectHandling()
        .build()

    val template = RestTemplate()
    template.requestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
    return template
}