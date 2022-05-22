package net.rickiekarp.loginserver.utils

import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.system.measureTimeMillis

@Component
class HashingUtil {

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun generateStrongPasswordHash(password: String): String {
        val iterations = 16
        val chars = password.toCharArray()
        val salt = password.toByteArray()

        val spec = PBEKeySpec(chars, salt, iterations, 200)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = skf.generateSecret(spec).encoded
        return toHex(hash)
    }

    private fun getSalt(): ByteArray {
        val sr = SecureRandom.getInstance("SHA1PRNG")
        val salt = ByteArray(16)
        sr.nextBytes(salt)
        return salt
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun toHex(array: ByteArray): String {
        val bi = BigInteger(1, array)
        val hex = bi.toString(16)
        val paddingLength = array.size * 2 - hex.length
        return if (paddingLength > 0) {
            String.format("%0" + paddingLength + "d", 0) + hex
        } else {
            hex
        }
    }

    private fun measureTime() {
        val password = "password"
        val time = measureTimeMillis {
            generateStrongPasswordHash(password)
        }
        println("$time ms")
    }
}
