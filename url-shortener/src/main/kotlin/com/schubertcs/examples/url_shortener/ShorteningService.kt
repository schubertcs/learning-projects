package com.schubertcs.examples.url_shortener

import com.github.f4b6a3.uuid.UuidCreator
import com.github.f4b6a3.uuid.codec.base.Base62Codec
import org.springframework.stereotype.Service
import java.net.URI

@Service
class ShorteningService {
    fun shorten(uriToShorten:URI) : String {
        val uuid = UuidCreator.getNameBasedSha1(uriToShorten.toString())
        val encoded = Base62Codec.INSTANCE.encode(uuid)
        return encoded
    }
}