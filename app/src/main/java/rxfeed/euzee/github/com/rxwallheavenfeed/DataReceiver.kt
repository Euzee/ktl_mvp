package rxfeed.euzee.github.com.rxwallheavenfeed

import rxfeed.euzee.github.com.rxwallheavenfeed.models.PictureLink
import java.util.*

interface DataReceiver {
    fun onNewData(parsedLinks: ArrayList<PictureLink>)
}
