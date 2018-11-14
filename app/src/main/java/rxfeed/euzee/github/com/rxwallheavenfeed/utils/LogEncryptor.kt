package rxfeed.euzee.github.com.rxwallheavenfeed.utils

import android.text.TextUtils
import android.util.Base64
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException

internal object LogEncryptor {

    private const val PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApaLxkZZ8dZ7atoPSyS42" +
            "Y1f/aDSt7ffHEKxgguJQNe647jMXyWzweqX3rZBcmcdi0itvXKLSzYR/TPfUaeoH" +
            "Z69PZPUyePthWztFil/UpHhs/ALUchYh2IKseJDzRSn1F08NO4t6lwujkFngFu8e" +
            "p47Q8XWnAAF/8xcN1XInNhI7YjN64FqIZd+/yxPxwkufNe7PBFa7EKY110yvhQgf" +
            "3DA63/dhfA5cAZjcrH3bBLzIEqSROE/9HBs+m3pXeP1r1eTKArougCaSODCGXm3J" +
            "xmcwiOYPHUI6rLJ52HdqFD679bbKsVeOyQ5rInX4f9zRUz5arvyC5JKsa+pMZZxv" +
            "rQIDAQAB"

    private var cipher: Cipher? = null


    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class, NoSuchPaddingException::class, InvalidKeyException::class)
    private fun init() {
        val publicBytes = Base64.decode(PUBLIC_KEY, Base64.DEFAULT)
        val keySpec = X509EncodedKeySpec(publicBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val pubKey = keyFactory.generatePublic(keySpec)
        cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING") //or try with "RSA"
        cipher!!.init(Cipher.ENCRYPT_MODE, pubKey)
    }

    fun encryptLogcat(message: String): String {
        var encoded = ""
        try {
            if (cipher == null)
                init()
            encoded = Base64.encodeToString(cipher!!.doFinal(message.toByteArray()), Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (TextUtils.isEmpty(encoded)) message else encoded
    }

    fun encryptLogs(txt: String): String {
        var encoded = ""
        try {
            if (cipher == null)
                init()
            encoded = (AESUtil.encrypt(txt) + "\n"
                    + Base64.encodeToString(cipher!!.doFinal(AESUtil.extractKey()), Base64.DEFAULT) + "\n"
                    + Base64.encodeToString(cipher!!.doFinal(AESUtil.extractIV()), Base64.DEFAULT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (TextUtils.isEmpty(encoded)) txt else encoded
    }

}
