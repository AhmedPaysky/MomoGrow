package com.paysky.momogrow.utilis

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.paysky.momogrow.R
import java.util.regex.Pattern

class MyUtils {
    companion object {
        fun getDlgProgress(context: Context?): Dialog {
            var dialog: Dialog? = null
            dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dlg_progress)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false);
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            return dialog
        }

        val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun isEmailValid(email: String): Boolean {
            return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
        }

    }

}