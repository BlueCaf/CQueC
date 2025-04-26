package com.bluecaf.cquec.util

import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

object Crypto {
    private const val AES_KEY = "1234567890123456"  // 16자
    private const val HMAC_KEY = "asdfekdiekikd2"
    private const val AES_TRANSFORMATION = "AES/ECB/PKCS5Padding"

    fun encryptWithHmac(plainText: String): String {
        val keySpec = SecretKeySpec(AES_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encrypted = cipher.doFinal(plainText.toByteArray())
        val base64Encrypted = Base64.getEncoder().encodeToString(encrypted)

        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(HMAC_KEY.toByteArray(), "HmacSHA256"))
        val hmac = mac.doFinal(base64Encrypted.toByteArray())
        val base64Hmac = Base64.getEncoder().encodeToString(hmac)

        return "$base64Encrypted::$base64Hmac"
    }

    fun decryptWithHmacCheck(encryptedInput: String): String? {
        val parts = encryptedInput.split("::")
        if (parts.size != 2) return null
        val (encryptedData, receivedHmac) = parts

        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(HMAC_KEY.toByteArray(), "HmacSHA256"))
        val expectedHmac = Base64.getEncoder().encodeToString(mac.doFinal(encryptedData.toByteArray()))

        if (expectedHmac != receivedHmac) return null  // 변조됨

        val cipher = Cipher.getInstance(AES_TRANSFORMATION)
        val keySpec = SecretKeySpec(AES_KEY.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedBytes)
    }
}
