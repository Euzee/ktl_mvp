package rxfeed.euzee.github.com.rxwallheavenfeed.net

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

internal abstract class UnsafeClient {

    // Create a trust manager that does not validate certificate chains
    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
    val unsafeOkHttpClient: OkHttpClient
        @SuppressLint("TrustAllX509TrustManager")
        get() {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                            chain: Array<java.security.cert.X509Certificate>,
                            authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                            chain: Array<java.security.cert.X509Certificate>,
                            authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts,
                        java.security.SecureRandom())
                val sslSocketFactory = sslContext
                        .socketFactory

                val builder = OkHttpClient.Builder()
                updateInterceptor(builder)
                builder.sslSocketFactory(sslSocketFactory)
                builder.hostnameVerifier { _, _ -> true }
                builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                builder.readTimeout(TIMEOUT, TimeUnit.SECONDS)

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

    protected abstract fun updateInterceptor(builder: OkHttpClient.Builder)

    companion object {

        private const val TIMEOUT: Long = 20
    }
}
