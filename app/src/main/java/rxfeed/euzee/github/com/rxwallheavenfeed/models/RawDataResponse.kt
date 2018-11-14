package rxfeed.euzee.github.com.rxwallheavenfeed.models

import java.util.*

class RawDataResponse(var rawData: String?) : BaseModel() {

    override val error: String = ""


    val parsedLinks: ArrayList<PictureLink>
        get() {
            var data = rawData
            var lastIndex: Int
            var fromIndex: Int
            val links = ArrayList<PictureLink>()
            while (data!!.contains("data-src")) {
                data = data.substring(data.indexOf("data-src=\""))
                fromIndex = data.indexOf("\"")
                lastIndex = data.indexOf("\"", fromIndex + 1)
                links.add(PictureLink(data.substring(fromIndex + 1, lastIndex)))
                data = data.substring(lastIndex + 1)
            }
            return links
        }

    override fun noErrors(): Boolean {
        return false
    }
}
