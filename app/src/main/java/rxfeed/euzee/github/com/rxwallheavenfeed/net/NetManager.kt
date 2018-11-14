package rxfeed.euzee.github.com.rxwallheavenfeed.net

import android.text.TextUtils
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rxfeed.euzee.github.com.rxwallheavenfeed.models.BaseModel
import rxfeed.euzee.github.com.rxwallheavenfeed.models.ConvertModel
import rxfeed.euzee.github.com.rxwallheavenfeed.models.RawDataResponse
import rxfeed.euzee.github.com.rxwallheavenfeed.utils.Logg
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class NetManager : INetworkManager {
    private val client: RestClient = RestClient()

    private val api: RestAPI?
        get() = client.get()


    override fun callFeed(page: Int, callback: NetworkCallback<ConvertModel>) {
        executeRequest(api!!.callFeed(page), callback)
    }

    override fun callRxFeed(page: Int, callback: NetworkCallback<*>?): Observable<Response<ResponseBody>> {
        return api!!.callRxFeed(page)
    }


    private fun urlEncode(model: String): RequestBody {
        var eModel = model
        try {
            val json = GsonWrapper.getGson()?.fromJson<JsonObject>(model, JsonObject::class.java)
            val builder = StringBuilder()
            for ((key, value) in json?.entrySet()!!) {
                if (builder.isNotEmpty()) {
                    builder.append("&")
                }
                builder.append(key.replace("\"".toRegex(), "")).append("=").append(URLEncoder.encode(value.toString().replace("\"".toRegex(), ""), "UTF-8"))
            }
            eModel = builder.toString()
        } catch (e: UnsupportedEncodingException) {
            Logg.e(TAG, "urlEncode()", e)
        }

        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), eModel)
    }

    private fun executeCallWithEmptyResponse(call: Call<Void>, callback: NetworkCallback<ConvertModel>) {
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback.onSuccess(null)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {}
        })
    }

    private fun <T : BaseModel> executeCall(call: Call<T>, callback: NetworkCallback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    if (response.body() != null && response.body()!!.noErrors()) {
                        callback.onSuccess(response.body())
                    } else {
                        callback.onFail(getErrorMessage(response))
                    }
                } else {
                    callback.onFail(getErrorMessage(response))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFail("Please ensure your device has internet access and try again")
            }

        })
    }

    private fun executeRequest(call: Call<ResponseBody>, networkCallback: NetworkCallback<ConvertModel>?) {
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (networkCallback != null) {
                    try {
                        if (response.isSuccessful) {
                            networkCallback.onSuccess(RawDataResponse(response.body()!!.string()))
                        } else {
                            networkCallback.onFail(response.message())
                        }
                    } catch (e: Exception) {
                        networkCallback.onFail("Cannot parse " + response.raw().toString())
                    }

                }
            }


            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                networkCallback!!.onFail(t.message)
            }
        })
    }

    private fun <T : BaseModel> getErrorMessage(response: Response<T>): String {
        var message: String? = ""

        if (response.body() != null) {
            message = response.body()!!.error
        }

        if (TextUtils.isEmpty(message) && response.errorBody() != null) {
            try {
                val json = response.errorBody()!!.string()
                val customError = GsonWrapper.getGson()?.fromJson<CustomError>(json, CustomError::class.java)

                message = customError?.text
            } catch (e: Exception) {
                Logg.e(TAG, "getErrorMessage()", e)
            }

        }

        if (TextUtils.isEmpty(message)) {
            message = response.message()
        }

        if (message!!.contains("Authentication credentials were not provided") || message.contains("Forbidden")) {
            Logg.e(TAG, message)
        }
        return message
    }

    private inner class CustomError {
        val detail: String? = null
        val error: String? = null

        val text: String?
            get() {
                var message = detail
                if (TextUtils.isEmpty(message)) {
                    message = error
                }
                return message
            }
    }

    companion object {

        private val TAG = NetManager::class.java.simpleName
    }

}

