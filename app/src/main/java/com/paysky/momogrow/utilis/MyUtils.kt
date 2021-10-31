package com.paysky.momogrow.utilis

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.paysky.momogrow.R

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
    }
}