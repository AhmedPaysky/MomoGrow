package com.paysky.momogrow.utilis

class Constants {
    companion object {
        const val ENVIRONMENT: String = "testtarget3"
        const val PREFS_NAME = "MOMO"

        class Preference {
            companion object {
                const val TERMINAL_ID: String = "terminal_id"
                const val MERCHANT_ID: String = "merchant_id"
                const val IS_REGISTERED: String = "is_register_completed"
                const val AUTH_TOKEN: String = "auth_token"
                const val FIREBASE_TOKEN = "fb_token"
                const val RECEIVED_AUTH: String = "received_auth"
                const val IS_LOGIN = "is_login"
                const val MOBILE_NUM = "mobile_num"
            }
        }
    }


}