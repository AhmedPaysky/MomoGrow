package com.paysky.momogrow.utilis

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.paysky.momogrow.utilis.Constants.Companion.PREFS_NAME

@SuppressLint("CommitPrefEdits")
class PreferenceProcessor(var context: Application) {

    init {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        prefs = EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

//        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = prefs?.edit()

    }

    companion object {
        private var mInstance: PreferenceProcessor? = null

        private var prefs: SharedPreferences? = null
        private var editor: Editor? = null

        fun getInstance(context: Application): PreferenceProcessor? {
            if (mInstance == null) {
                mInstance = PreferenceProcessor(context)
            }
            return mInstance
        }

        fun setInt(key: String?, value: Int) {
            editor!!.putInt(key, value)
            editor!!.apply()
        }

        fun getInt(key: String?, defValue: Int?): Int {
            return prefs!!.getInt(key, defValue!!)
        }

        fun setStr(key: String?, value: String?) {
            editor!!.putString(key, value)
            editor!!.apply()
        }

        fun getStr(key: String?, defValue: String?): String? {
            return prefs!!.getString(key, defValue)
        }

        fun setBool(key: String?, value: Boolean) {
            editor!!.putBoolean(key, value)
            editor!!.apply()
        }

        fun getBool(key: String?, defValue: Boolean?): Boolean {
            return prefs!!.getBoolean(key, defValue!!)
        }

        fun clear() {
            editor?.clear()?.apply()
        }
    }
}