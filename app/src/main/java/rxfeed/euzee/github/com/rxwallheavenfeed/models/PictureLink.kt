package rxfeed.euzee.github.com.rxwallheavenfeed.models

import java.util.*

class PictureLink(var link: String?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PictureLink) return false
        val that = other as PictureLink?
        return link == that!!.link
    }

    override fun hashCode(): Int {

        return Objects.hash(link)
    }
}
