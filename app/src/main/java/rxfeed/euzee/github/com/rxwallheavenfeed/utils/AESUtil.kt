package rxfeed.euzee.github.com.rxwallheavenfeed.utils

import android.util.Base64
import java.security.Key
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.spec.AlgorithmParameterSpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

internal enum class AESUtil {
    ;

    companion object {
        private var rawKey: ByteArray? = null
        private var rawIV: ByteArray? = null

        fun encrypt(src: String): String {
            var encrypted = ""
            try {
                val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, makeKey(), makeIv())
                encrypted = Base64.encodeToString(cipher.doFinal(src.toByteArray()), Base64.DEFAULT)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            return encrypted
        }

        fun extractKey(): ByteArray {
            val encoded = toHex(rawKey!!).toByteArray()
            try {
                makeKey()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return encoded
        }

        fun extractIV(): ByteArray {
            val encoded = Arrays.copyOf(rawIV!!, rawIV!!.size)
            try {
                makeIv()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return encoded
        }

        fun makeIv(): AlgorithmParameterSpec {
            val spec = IvParameterSpec(toHex(SecureRandom().generateSeed(8)).toByteArray())
            rawIV = spec.iv
            return spec
        }

        fun makeKey(): Key? {
            try {
                val md = MessageDigest.getInstance("MD5")
                val key = md.digest(toHex(genRawKey()).toByteArray())
                return SecretKeySpec(key, "AES")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        private fun toHex(data: ByteArray): String {
            val sb = StringBuilder()
            for (b in data) {
                sb.append(String.format("%02X", b))
            }
            return sb.toString()
        }

        @Throws(Exception::class)
        private fun genRawKey(): ByteArray {
            val kgen = KeyGenerator.getInstance("AES")
            val sr = SecureRandom.getInstance("SHA1PRNG")
            sr.setSeed(sr.generateSeed(32))
            kgen.init(128, sr) // 192 and 256 bits may not be available
            val skey = kgen.generateKey()
            rawKey = skey.encoded
            return rawKey as ByteArray
        }
    }
}
