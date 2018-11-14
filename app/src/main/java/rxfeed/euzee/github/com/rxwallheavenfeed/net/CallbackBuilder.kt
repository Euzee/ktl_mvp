package rxfeed.euzee.github.com.rxwallheavenfeed.net

import rxfeed.euzee.github.com.rxwallheavenfeed.models.RawDataResponse

class CallbackBuilder {

    private var callSuccess: CallWrapper? = null
    private var callFailed: CallWrapper? = null

    private val callback = object : NetworkCallback<RawDataResponse> {
        override fun onSuccess(response: RawDataResponse?) {
            callSuccess?.call(response)
        }

        override fun onFail(message: String?) {
            callFailed?.call(null)
        }
    }

    fun success(successListener: CallWrapper): CallbackBuilder {
        callSuccess = successListener
        return this
    }

    fun fail(failListener: CallWrapper): CallbackBuilder {
        callFailed = failListener
        return this
    }

    fun build(): NetworkCallback<*> {
        return callback
    }


}
