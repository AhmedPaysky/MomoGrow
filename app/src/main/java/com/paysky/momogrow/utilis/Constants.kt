package com.paysky.momogrow.utilis

class Constants {
    companion object {
        const val PREFS_NAME = "MOMO"

        class Preference {
            companion object {
                const val IS_REGISTERED: String = "is_register_completed"
                const val AUTH_TOKEN: String = "auth_token"
                const val FIREBASE_TOKEN = "fb_token"
                const val IS_LOGIN = "is_login"
            }
        }
    }


}