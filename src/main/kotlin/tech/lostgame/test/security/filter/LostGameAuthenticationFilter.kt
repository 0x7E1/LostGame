package tech.lostgame.test.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import tech.lostgame.test.exception.InvalidAuthenticationException
import tech.lostgame.test.exception.MissingAuthenticationException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

@Component
class LostGameAuthenticationFilter(
    @Value("\${secret}") private val secret: String
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Sign")
        val requestBody = getRequestBody(request)

        if (token != null) {
            if (!isValid(token, requestBody)) {
                throw throw InvalidAuthenticationException("Bad credentials. Please provide valid \"Sign\" value.")
            }
        } else {
            throw MissingAuthenticationException("Authentication header (\"Sign\") hasn't been provided.")
        }

        filterChain.doFilter(request, response)
    }

    private fun getRequestBody(request: HttpServletRequest): String {
        val inputStream = request.inputStream
        return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)
    }

    private fun isValid(signature: String, requestBody: String): Boolean {
        val md = MessageDigest.getInstance("MD5")
        val md5HashBytes = md.digest(requestBody.plus(secret).toByteArray())
        val md5Hash = bytesToHex(md5HashBytes).lowercase()

        return md5Hash == signature
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = hexArray[v ushr 4]
            hexChars[i * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }
}