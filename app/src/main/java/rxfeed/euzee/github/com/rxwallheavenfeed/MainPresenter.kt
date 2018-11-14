package rxfeed.euzee.github.com.rxwallheavenfeed

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rxfeed.euzee.github.com.rxwallheavenfeed.models.RawDataResponse
import rxfeed.euzee.github.com.rxwallheavenfeed.net.INetworkManager
import rxfeed.euzee.github.com.rxwallheavenfeed.utils.Logg

class MainPresenter(val view: MainView, private var manager: INetworkManager? = null, private val receiver: DataReceiver) {


    private var nextPage = 0
    private var loading: Boolean = false

    init {
        loadData()
    }

    @SuppressLint("CheckResult")
    fun loadData() {
        if (!loading) {
            nextPage++
            loading = true
            manager?.callRxFeed(nextPage, null)
                    ?.subscribeOn(Schedulers.newThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.doOnError {
                        Logg.e("net call", it.message!!)
                    }
                    ?.subscribe { responseBodyCall ->
                        receiver.onNewData(RawDataResponse(responseBodyCall.body()?.string()).parsedLinks)
                        loading = false
                    }
//            manager?.callFeed(nextPage, CallbackBuilder().success({ model ->
//                receiver.onNewData(model.parsedLinks)
//                loading = false
//            }).build())
        }
    }

    interface MainView
    // in case of any actions with UI
    // add functions here ( e.g. toast,dialog,etc.)
    // and call from presenter
}
