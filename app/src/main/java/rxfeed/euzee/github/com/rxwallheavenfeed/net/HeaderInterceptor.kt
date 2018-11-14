package rxfeed.euzee.github.com.rxwallheavenfeed.net

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                //                .addHeader(Keys.HEADER_ACCEPT, Keys.ACCEPT_TYPE)
                //                .addHeader(PrefKeys.HEADER_TOKEN.toString(), App.getDataManager().getToken())
                .build()
        return chain.proceed(request)
    }
}
