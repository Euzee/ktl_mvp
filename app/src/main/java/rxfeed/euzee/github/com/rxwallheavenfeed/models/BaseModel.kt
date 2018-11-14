package rxfeed.euzee.github.com.rxwallheavenfeed.models

abstract class BaseModel : ConvertModel() {

    abstract val error: String
    abstract fun noErrors(): Boolean

}
