package rxfeed.euzee.github.com.rxwallheavenfeed.utils

import rxfeed.euzee.github.com.rxwallheavenfeed.BuildConfig

object GeneralUtils {

    internal val isDebugOnly: Boolean
        get() = BuildConfig.DEBUG
}
