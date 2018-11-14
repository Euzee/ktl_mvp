package rxfeed.euzee.github.com.rxwallheavenfeed.models

import rxfeed.euzee.github.com.rxwallheavenfeed.net.GsonWrapper


abstract class ConvertModel {

    fun toJson(): String {
        return GsonWrapper.getGson()!!.toJson(this)
    }
}
