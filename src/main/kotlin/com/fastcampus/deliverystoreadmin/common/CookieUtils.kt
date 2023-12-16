package com.fastcampus.deliverystoreadmin.common

import jakarta.servlet.http.Cookie

class CookieUtils {
    companion object {
        fun createAuthCookie(
            cookieKey: String,
            cookieValue: String,
            cookiePath: String = "/",
            cookieMaxAge: Int = 60 * 60 * 24 * 7
        ): Cookie {
            val cookie = Cookie(cookieKey, cookieValue)
            cookie.path = cookiePath
            cookie.maxAge = cookieMaxAge
            return cookie
        }
    }
}