package rxfeed.euzee.github.com.rxwallheavenfeed.net


import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rxfeed.euzee.github.com.rxwallheavenfeed.Constants

interface RestAPI {

    @GET(Constants.API_ROOT + Constants.FEED_ENDPOINT)
    fun callFeed(@Query("page") page: Int): Call<ResponseBody>

    @GET(Constants.API_ROOT + Constants.FEED_ENDPOINT)
    fun callRxFeed(@Query("page") page: Int): Observable<Response<ResponseBody>>

}
