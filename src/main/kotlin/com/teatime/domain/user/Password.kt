package com.teatime.domain.user

import org.apache.tomcat.util.codec.binary.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object Password {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private val iterations = 20 * 1000
    private val saltLen = 32
    private val desiredKeyLen = 256

    /** Computes a salted PBKDF2 hash of given plaintext password
     * suitable for storing in a database.
     * Empty passwords are not supported.  */
    @Throws(Exception::class)
    fun getSaltedHash(password: String): String {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen)
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt)
    }

    /** Checks whether given plaintext password corresponds
     * to a stored salted hash of the password.  */
    @Throws(Exception::class)
    fun check(password: String, stored: String): Boolean {
        val saltAndHash = stored.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        check(saltAndHash.size == 2) { "The stored password must have the form 'salt\$hash'" }
        val hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[0]))
        return hashOfInput == saltAndHash[1]
    }

    // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
    @Throws(Exception::class)
    private fun hash(password: String?, salt: ByteArray): String {
        require(!(password == null || password.length == 0)) { "Empty passwords are not supported." }
        val f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val key = f.generateSecret(PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen))
        return Base64.encodeBase64String(key.encoded)
    }
}