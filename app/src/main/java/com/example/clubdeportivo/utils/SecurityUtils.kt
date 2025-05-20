package com.example.clubdeportivo.utils

import java.security.MessageDigest

object SecurityUtils {
    fun sha256(input: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray(Charsets.UTF_8))

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
}