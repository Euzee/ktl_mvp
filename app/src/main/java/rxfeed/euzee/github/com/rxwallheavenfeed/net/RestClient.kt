package rxfeed.euzee.github.com.rxwallheavenfeed.net

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rxfeed.euzee.github.com.rxwallheavenfeed.BuildConfig
import rxfeed.euzee.github.com.rxwallheavenfeed.Constants
import rxfeed.euzee.github.com.rxwallheavenfeed.utils.Logg

internal class RestClient : UnsafeClient() {

    fun get(): RestAPI? {
        if (REST_API == null)
            setUpRestClient()
        return REST_API
    }

    fun refresh() {
        if (REST_API != null)
            REST_API = null
        setUpRestClient()
    }

    private fun setUpRestClient() {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_ROOT)
                .client(unsafeOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        REST_API = retrofit.create<RestAPI>(RestAPI::class.java)
    }

    override fun updateInterceptor(builder: OkHttpClient.Builder) {
        builder.addNetworkInterceptor(HeaderInterceptor())

        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor(Logg())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)
        }
    }

    companion object {

        private var REST_API: RestAPI? = null
    }

}
