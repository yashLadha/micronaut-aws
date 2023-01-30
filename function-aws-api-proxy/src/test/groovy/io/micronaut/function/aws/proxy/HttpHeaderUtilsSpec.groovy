package io.micronaut.function.aws.proxy

import spock.lang.Specification
import spock.lang.Unroll

class HttpHeaderUtilsSpec extends Specification {

    @Unroll
    void "HTTP Header #header normalized is #expected"(String header, String expected) {
        expect:
        expected == HttpHeaderUtils.normalizeHttpHeaderCase(header)
        where:
        header                             | expected
        "FooBar"                           | "FooBar"
        "accept"                           | "Accept"
        "accept-ch"                        | "Accept-CH"
        "accept-ch-lifetime"               | "Accept-CH-Lifetime"
        "accept-charset"                   | "Accept-Charset"
        "accept-encoding"                  | "Accept-Encoding"
        "accept-language"                  | "Accept-Language"
        "accept-ranges"                    | "Accept-Ranges"
        "accept-patch"                     | "Accept-Patch"
        "access-control-allow-credentials" | "Access-Control-Allow-Credentials"
        "access-control-allow-headers"     | "Access-Control-Allow-Headers"
        "access-control-allow-methods"     | "Access-Control-Allow-Methods"
        "access-control-allow-origin"      | "Access-Control-Allow-Origin"
        "access-control-expose-headers"    | "Access-Control-Expose-Headers"
        "access-control-max-age"           | "Access-Control-Max-Age"
        "access-control-request-headers"   | "Access-Control-Request-Headers"
        "access-control-request-method"    | "Access-Control-Request-Method"
        "age"                              | "Age"
        "allow"                            | "Allow"
        "authorization"                    | "Authorization"
        "authorization-info"               | "Authorization-Info"
        "cache-control"                    | "Cache-Control"
        "connection"                       | "Connection"
        "content-base"                     | "Content-Base"
        "content-disposition"              | "Content-Disposition"
        "content-dpr"                      | "Content-DPR"
        "content-encoding"                 | "Content-Encoding"
        "content-language"                 | "Content-Language"
        "content-length"                   | "Content-Length"
        "content-location"                 | "Content-Location"
        "content-transfer-encoding"        | "Content-Transfer-Encoding"
        "content-md5"                      | "Content-MD5"
        "content-range"                    | "Content-Range"
        "content-type"                     | "Content-Type"
        "cookie"                           | "Cookie"
        "cross-origin-resource-policy"     | "Cross-Origin-Resource-Policy"
        "date"                             | "Date"
        "device-memory"                    | "Device-Memory"
        "downlink"                         | "Downlink"
        "dpr"                              | "DPR"
        "ect"                              | "ECT"
        "etag"                             | "ETag"
        "expect"                           | "Expect"
        "expires"                          | "Expires"
        "feature-policy"                   | "Feature-Policy"
        "forwarded"                        | "Forwarded"
        "from"                             | "From"
        "host"                             | "Host"
        "if-match"                         | "If-Match"
        "if-modified-since"                | "If-Modified-Since"
        "if-none-match"                    | "If-None-Match"
        "if-range"                         | "If-Range"
        "if-unmodified-since"              | "If-Unmodified-Since"
        "last-modified"                    | "Last-Modified"
        "link"                             | "Link"
        "location"                         | "Location"
        "max-forwards"                     | "Max-Forwards"
        "origin"                           | "Origin"
        "pragma"                           | "Pragma"
        "proxy-authenticate"               | "Proxy-Authenticate"
        "proxy-authorization"              | "Proxy-Authorization"
        "range"                            | "Range"
        "referer"                          | "Referer"
        "referrer-policy"                  | "Referrer-Policy"
        "retry-after"                      | "Retry-After"
        "rtt"                              | "RTT"
        "save-data"                        | "Save-Data"
        "sec-websocket-key1"               | "Sec-WebSocket-Key1"
        "sec-websocket-key2"               | "Sec-WebSocket-Key2"
        "sec-websocket-location"           | "Sec-WebSocket-Location"
        "sec-websocket-origin"             | "Sec-WebSocket-Origin"
        "sec-websocket-protocol"           | "Sec-WebSocket-Protocol"
        "sec-websocket-version"            | "Sec-WebSocket-Version"
        "sec-websocket-key"                | "Sec-WebSocket-Key"
        "sec-websocket-accept"             | "Sec-WebSocket-Accept"
        "server"                           | "Server"
        "set-cookie"                       | "Set-Cookie"
        "set-cookie2"                      | "Set-Cookie2"
        "sourcemap"                        | "SourceMap"
        "te"                               | "TE"
        "trailer"                          | "Trailer"
        "transfer-encoding"                | "Transfer-Encoding"
        "upgrade"                          | "Upgrade"
        "user-agent"                       | "User-Agent"
        "vary"                             | "Vary"
        "via"                              | "Via"
        "viewport-width"                   | "Viewport-Width"
        "warning"                          | "Warning"
        "websocket-location"               | "WebSocket-Location"
        "websocket-origin"                 | "WebSocket-Origin"
        "websocket-protocol"               | "WebSocket-Protocol"
        "width"                            | "Width"
        "www-authenticate"                 | "WWW-Authenticate"
        "x-auth-token"                     | "X-Auth-Token"
    }

}
