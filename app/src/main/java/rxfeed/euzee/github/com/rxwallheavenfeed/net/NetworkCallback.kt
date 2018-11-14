package rxfeed.euzee.github.com.rxwallheavenfeed.net

import rxfeed.euzee.github.com.rxwallheavenfeed.models.ConvertModel

interface NetworkCallback<T : ConvertModel> {
    fun onSuccess(response: T?)

    fun onFail(message: String?)

}
