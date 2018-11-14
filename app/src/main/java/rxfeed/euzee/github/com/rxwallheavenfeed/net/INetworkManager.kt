package rxfeed.euzee.github.com.rxwallheavenfeed.net

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import rxfeed.euzee.github.com.rxwallheavenfeed.models.ConvertModel

interface INetworkManager {

    fun callFeed(page: Int, callback: NetworkCallback<ConvertModel>)

    fun callRxFeed(page: Int, callback: NetworkCallback<*>?): Observable<Response<ResponseBody>>

}
