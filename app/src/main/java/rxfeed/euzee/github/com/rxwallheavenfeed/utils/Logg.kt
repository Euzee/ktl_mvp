package rxfeed.euzee.github.com.rxwallheavenfeed.utils

import android.util.Log

import okhttp3.logging.HttpLoggingInterceptor

import rxfeed.euzee.github.com.rxwallheavenfeed.utils.GeneralUtils.isDebugOnly


class Logg : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        if (isDebugOnly) {
            Log.w("Network", message)
        }
    }

    companion object {

        private val ENCRYPTED_TAG = "FRIDAY_X_CAGE"

        fun e(tag: String, message: String, t: Throwable) {
            Log.e(tag, message, t)
        }

        fun e(tag: String, message: String) {
            if (!isDebugOnly)
                Log.e(ENCRYPTED_TAG, LogEncryptor.encryptLogcat(message))
            else
                Log.e(tag, message)
        }

        fun i(tag: String, message: String, t: Throwable) {
            Log.i(tag, message, t)
        }

        fun i(tag: String, message: String) {
            if (!isDebugOnly)
                Log.i(ENCRYPTED_TAG, LogEncryptor.encryptLogcat(message))
            else
                Log.i(tag, message)
        }

        fun v(tag: String, message: String, t: Throwable) {
            Log.v(tag, message, t)
        }

        fun v(tag: String, message: String) {
            if (!isDebugOnly)
                Log.v(ENCRYPTED_TAG, LogEncryptor.encryptLogcat(message))
            else
                Log.v(tag, message)
        }

        fun w(tag: String, message: String, t: Throwable) {
            Log.w(tag, message, t)
        }

        fun w(tag: String, message: String) {
            if (!isDebugOnly)
                Log.w(ENCRYPTED_TAG, LogEncryptor.encryptLogcat(message))
            else
                Log.w(tag, message)
        }

        fun d(tag: String, message: String, t: Throwable) {
            Log.d(tag, message, t)
        }

        fun d(tag: String, message: String) {
            if (!isDebugOnly)
                Log.d(ENCRYPTED_TAG, LogEncryptor.encryptLogcat(message))
            else
                Log.d(tag, message)
        }
    }

}
